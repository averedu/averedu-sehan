<template>
  <div class="flex-1 overflow-y-auto p-6">
    <SearchBox :onSearch="search" :colCount="3">
      <template #col1>
        <label class="search-label">사용자 ID</label>
        <input @keyup.enter="search" v-model="param.loginId" type="text" class="search-input" />
      </template>
      <template #col2>
        <label class="search-label">사용자명</label>
        <input @keyup.enter="search" v-model="param.userName" type="text" class="search-input" />
      </template>
      <template #col3>
        <label class="search-label">사용여부</label>
        <select @change="search" v-model="param.useYn" class="search-select">
          <option value="">전체</option>
          <option value="Y">사용</option>
          <option value="N">미사용</option>
        </select>
      </template>
    </SearchBox>
    <div
      class="mt-4 bg-white dark:bg-[#252731] p-6 rounded-lg shadow-md md:col-span-1 whitespace-nowrap overflow-x-auto relative"
    >
      <!-- 제목과 버튼을 한 줄에 배치 -->
      <div class="flex items-center justify-between mb-4">
        <h2 class="text-xl font-semibold leading-tight text-gray-700">사용자 목록</h2>
        <!-- <ButtonTest
          @add-row="mainGridAdd()"
          @delete-item="deleteItemMain"
          @save-data="saveDataMain"
          @download-excel="downloadExcelMain"
        /> -->
      </div>
      <!-- 그리드 영역 -->
      <div class="flex-auto">
        <ag-grid-vue
          :columnDefs="mainColumnDefs"
          :rowData="mainRowData"
          @cell-clicked="onCellClicked"
          @grid-ready="onGridReady"
          :gridOptions="gridOptions"
          style="width: 100%; height: 600px"
        ></ag-grid-vue>
      </div>
    </div>
  </div>
</template>

<script setup>
import axios from 'axios'
import { AllCommunityModule, ModuleRegistry, provideGlobalGridOptions } from 'ag-grid-community'
import { AgGridVue } from 'ag-grid-vue3'
import { ref } from 'vue'
import ButtonTest from '@/components/ButtonTest.vue'
import SearchBox from '@/components/SearchBox.vue'

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

let userDataInfo = ref({
  loginId: '',
  pwd: '',
  pwdErrFreq: '',
  pwdChgDttm: '',
  userStCd: '',
  flLoginDttm: '',
  persNo: '',
})

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

let param = ref({
  loginId: '',
  userName: '',
  useYn: '',
})

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
mainGridCall(param)
</script>

<style lang="scss" scoped></style>
