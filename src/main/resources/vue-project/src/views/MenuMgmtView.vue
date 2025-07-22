<template>
  <div class="flex-1 h-full min-h-0 flex flex-col overflow-y-auto p-6">
    <SearchBox :autoSearch="false" :onSearch="search" :items="items" v-model="param" />
    <div
      class="mt-4 bg-white dark:bg-[#252731] p-6 rounded-lg shadow-md md:col-span-1 whitespace-nowrap overflow-x-auto relative flex-1 min-h-0 flex flex-col"
    >
      <!-- 제목과 버튼을 한 줄에 배치 -->
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-xl font-semibold leading-tight text-gray-700">메뉴 목록</h2>
        <GridButton
          :featureAuth="programFeatureAuth"
          @add-item="addItem"
          @delete-item="deleteItem"
          @save-data="saveData"
          @download-excel="downloadExcel"
        />
      </div>
      <!-- 그리드 영역 -->
      <div class="flex-1 min-h-0 overflow-hidden">
        <ag-grid-vue
          :columnDefs="mainColumnDefs"
          :rowData="mainRowData"
          @cell-clicked="onCellClicked"
          @cell-value-changed="onCellValueChanged"
          @grid-ready="onGridReady"
          :gridOptions="gridOptions"
          style="width: 100%; height: 100%"
        ></ag-grid-vue>
      </div>
    </div>
  </div>
</template>

<script setup>
// =======================[외부 라이브러리 및 컴포넌트 import]=======================
import axios from 'axios'
import { AllCommunityModule, ModuleRegistry, provideGlobalGridOptions } from 'ag-grid-community'
import { AgGridVue } from 'ag-grid-vue3'
import { ref } from 'vue'
import GridButton from '@/components/GridButton.vue'
import SearchBox from '@/components/SearchBox.vue'

// =======================[공통코드/셀렉트 옵션 정의]=======================
/* const useYnOptions = [
  { value: '', label: '전체' },
  { value: 'Y', label: '사용' },
  { value: 'N', label: '미사용' },
] */

// =======================[조회조건 필드 및 파라미터 초기화]=======================
const items = [
  { name: 'menuId', label: '메뉴 ID', type: 'text' },
  { name: 'menuNm', label: '메뉴명', type: 'text' },
  { name: 'pgmPathNm', label: '프로그램경로', type: 'text' },
]

// 조회 파라미터(초기값 자동 생성)
const param = ref({
  ...Object.fromEntries(items.map((item) => [item.name, ''])),
})

// =======================[ag-Grid 글로벌 설정 및 옵션]=======================
ModuleRegistry.registerModules([AllCommunityModule])
provideGlobalGridOptions()

const gridOptions = {
  rowSelection: {
    mode: 'multiRow',
    headerCheckbox: true,
  },
  defaultColDef: {
    headerClass: 'centered',
    cellClass: 'centered',
  },
}

// =======================[ag-Grid API, 컬럼, 데이터 정의]=======================
const programFeatureAuth = ref({
  add: true,
  delete: true,
  save: true,
  download: true,
})
const gridApi = ref()
const columnApi = ref()
const mainRowData = ref([])
const mainColumnDefs = [
  {
    field: 'status',
    headerName: '상태',
    width: 70,
    cellRenderer: (params) => {
      if (params.value === 'D') {
        return '<img src="/main/path_to_x_image.png" style="object-fit: cover;" alt="삭제" />'
      } else if (params.value === 'U') {
        return '<img src="/main/path_to_u_image.png" alt="변경" />'
      } else if (params.value === 'I') {
        return '<img src="/main/path_to_n_image.png" alt="추가" />'
      }
      return ''
    },
  },
  {
    field: 'menuId',
    headerName: '메뉴ID',
    width: 200,
    editable: (params) => params.data.status === 'I',
    editType: 'fullRow',
    required: true,
  },
  {
    field: 'pgmNm',
    headerName: '프로그램명',
    width: 200,
  },
  {
    field: 'menuNm',
    headerName: '메뉴명',
    width: 200,
    editable: true,
    editType: 'fullRow',
    required: true,
  },
  {
    field: 'pgmPathNm',
    headerName: '프로그램 경로명',
    width: 200,
  },
  {
    field: 'sortSeq',
    headerName: '정렬순번',
    width: 200,
    editable: true,
    editType: 'fullRow',
    required: true,
  },
  {
    field: 'pgmId',
    headerName: '프로그램ID',
    width: 200,
    editable: true,
    editType: 'fullRow',
    required: true,
  },
]
// =======================[사용자 상세 정보 상태]=======================
const menuDataInfo = ref({
  status: 'I',
  menuId: '',
  pgmNm: '',
  menuNm: '',
  sortSeq: '',
  pgmId: '',
})

// =======================[ag-Grid 이벤트 핸들러]=======================
const onGridReady = (params) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
  console.log('gridApi : ', gridApi.value)
  console.log('columnApi : ' + columnApi.value)
}

const onCellClicked = (params) => {
  console.log('cell click : ', params.data)
  menuDataInfo.value = params.data
}

const onCellValueChanged = (params) => {
  // params.data는 mainRowData의 참조이지만, 반응형 보장을 위해 새 객체로 교체
  const idx = mainRowData.value.findIndex((row) => row === params.data)
  if (idx !== -1) {
    // status 자동 변경(예: 신규가 아니면 수정으로)
    const newRow = { ...params.data }
    if (newRow.status !== 'I') newRow.status = 'U'
    mainRowData.value[idx] = newRow
  }
}

// =======================[조회 및 데이터 로드 함수]=======================
const search = () => {
  console.log('param : ', param)

  const obj = Object.assign({}, param.value)
  console.log('obj : ', obj)

  axios
    .post('/restApi/prj/sys/selectCommonMenuList', obj)
    .then((restApi) => {
      console.log('restApi : ', restApi)
      if (restApi.status === 200) {
        mainRowData.value = restApi.data
        console.log(mainRowData.value)
      }
    })
    .catch((error) => {
      console.log('mainGrid load error', error)
    })
}

// =======================[행 추가 함수]=======================
const defaultUserRow = {
  status: 'I',
  menuId: '자동채번',
  menuNm: '',
  pgmId: '',
  sortSeq: 0,
}

const addItem = () => {
  mainRowData.value.unshift({ ...defaultUserRow })
}

// =======================[삭제 기능]=======================
const deleteItem = () => {
  const selectedNodes = gridApi.value.getSelectedNodes()
  if (selectedNodes.length === 0) {
    console.error('삭제할 행을 선택해주세요.')
    return
  }
  const selectedIds = selectedNodes.map((node) => node.data.menuId)
  mainRowData.value = mainRowData.value.map((row) =>
    selectedIds.includes(row.menuId) ? { ...row, status: 'D' } : row,
  )
}

// =======================[저장 기능]=======================
const sanitizeRow = (row) => ({
  ...row,
  menuId: row.menuId ?? '',
  pgmNm: row.pgmNm ?? '',
  pgmId: row.pgmId ?? '',
  sortSeq: row.sortSeq ?? '',
})

const saveData = () => {
  if (!gridApi.value) {
    console.error('gridApi가 없습니다.')
    return
  }

  gridApi.value.stopEditing()

  const rowData = []
  gridApi.value.forEachNode((node) => rowData.push(sanitizeRow(node.data)))

  // status가 I, U, D인 데이터만 추출
  const saveRows = rowData.filter((row) => ['I', 'U', 'D'].includes(row.status))

  if (saveRows.length === 0) {
    console.error('저장할 데이터가 없습니다.')
    return
  }

  console.log('저장할 데이터:', saveRows)

  axios
    .post('/restApi/prj/sys/saveCommonMenuList', saveRows)
    .then((response) => {
      if (response.status === 200) {
        console.log(response.data || '저장 성공')
        alert('저장 성공')
        search() // 저장 후 데이터 재조회
      }
    })
    .catch((error) => {
      console.log('저장 실패: ' + (error.response?.data || error.message))
    })
}

// =======================[엑셀 다운로드 기능]=======================
const downloadExcel = () => {
  if (!gridApi.value) {
    console.error('gridApi가 없습니다.')
    return
  }

  const rowData = []
  gridApi.value.forEachNode((node) => rowData.push(node.data))

  if (rowData.length === 0) {
    console.error('다운로드할 데이터가 없습니다.')
    return
  }

  axios
    .post('/restApi/prj/sys/commonUser/downloadExcel.do', rowData, { responseType: 'blob' })
    .then((response) => {
      const url = window.URL.createObjectURL(new Blob([response.data]))
      const link = document.createElement('a')
      link.href = url
      link.setAttribute('download', 'user_data.xlsx')
      document.body.appendChild(link)
      link.click()
      document.body.removeChild(link)
    })
    .catch((error) => {
      console.error('엑셀 다운로드 실패:', error)
    })
}
</script>

<style lang="scss" scoped></style>
