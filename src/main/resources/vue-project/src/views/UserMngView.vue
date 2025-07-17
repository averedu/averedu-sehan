<template>
  <div class="flex-1 h-full min-h-0 flex flex-col overflow-y-auto p-6">
    <SearchBox :onSearch="search" :items="items" v-model="param" />
    <div
      class="mt-4 bg-white dark:bg-[#252731] p-6 rounded-lg shadow-md md:col-span-1 whitespace-nowrap overflow-x-auto relative flex-1 min-h-0 flex flex-col"
    >
      <!-- 제목과 버튼을 한 줄에 배치 -->
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-xl font-semibold leading-tight text-gray-700">사용자 목록</h2>
        <GridButton
          :featureAuth="'CUDE'"
          @add-row="mainGridAdd"
          @delete-item="deleteItemMain"
          @save-data="saveDataMain"
          @download-excel="downloadExcelMain"
        />
      </div>
      <!-- 그리드 영역 -->
      <div class="flex-1 min-h-0 overflow-hidden">
        <ag-grid-vue
          :columnDefs="mainColumnDefs"
          :rowData="mainRowData"
          @cell-clicked="onCellClicked"
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
const useYnOptions = [
  { value: '', label: '전체' },
  { value: 'Y', label: '사용' },
  { value: 'N', label: '미사용' },
]

// =======================[조회조건 필드 정의 및 파라미터 초기화]=======================
const items = [
  { name: 'loginId', label: '사용자 ID', type: 'text' },
  { name: 'userName', label: '사용자명', type: 'text' },
  { name: 'useYn', label: '사용여부', type: 'select', options: useYnOptions },
  { name: 'notSupported', label: '지원안함', type: 'date' }, // 지원하지 않는 타입 샘플
]

// 조회 파라미터(초기값 자동 생성)
let param = ref({
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
    editable: true,
    editType: 'fullRow',
  },
}

// =======================[ag-Grid API, 컬럼, 데이터 정의]=======================
const gridApi = ref()
const columnApi = ref()
const mainRowData = ref([])

const mainColumnDefs = [
  {
    field: 'status',
    headerName: '상태',
    width: 100,
    cellRenderer: (params) => {
      if (params.value === 'D') {
        return '<img src="path_to_x_image.png" alt="삭제" />'
      } else if (params.value === 'U') {
        return '<img src="path_to_u_image.png" alt="변경" />'
      } else if (params.value === 'N') {
        return '<img src="path_to_n_image.png" alt="추가" />'
      }
      return ''
    },
  },
  { field: 'loginId', headerName: '사용자 ID', width: 200 },
  { field: 'pwd', headerName: '비밀번호', width: 200 },
  { field: 'pwdErrFreq', headerName: '비밀번호 오류 횟수', width: 200 },
  { field: 'pwdChgDttm', headerName: '비밀번호 변경 일시', width: 200 },
  { field: 'userStCd', headerName: '사용자 상태 코드', width: 200 },
  { field: 'flLoginDttm', headerName: '최초 로그인 일시', width: 200 },
  { field: 'persNo', headerName: '개인번호', width: 200 },
]

const mainGridAdd = () => {
  const mainAddRow = {
    status: 'N',
  }
  mainRowData.value.unshift(mainAddRow)
}

// =======================[사용자 상세 정보 상태]=======================
let userDataInfo = ref({
  loginId: '',
  pwd: '',
  pwdErrFreq: '',
  pwdChgDttm: '',
  userStCd: '',
  flLoginDttm: '',
  persNo: '',
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
  userDataInfo.value = params.data
}

// =======================[조회 및 데이터 로드 함수]=======================
const search = () => {
  mainGridCall(param)
}

const mainGridCall = (param) => {
  console.log('param : ', param)

  const obj = Object.assign({}, param.value)
  console.log('obj : ', obj)

  axios
    .post('/restApi/prj/sys/commonUser/selectCommonUserList.do', obj)
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

// =======================[자동조회 예시(주석처리)]=======================
// mainGridCall(param)
</script>

<style lang="scss" scoped></style>
