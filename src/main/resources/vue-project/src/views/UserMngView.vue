<template>
  <div class="flex-1 overflow-y-auto p-6">
    <SearchBox :onSearch="search" :colCount="3">
      <template #col1>
        <label class="search-label">사용자 ID</label>
        <input @keyup.enter="search" v-model="param.deptNm" type="text" class="search-input" />
      </template>
      <template #col2>
        <label class="search-label">사용자명</label>
        <input @keyup.enter="search" v-model="param.deptCd" type="text" class="search-input" />
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
        <ButtonTest
          @add-row="mainGridAdd()"
          @delete-item="deleteItemMain"
          @save-data="saveDataMain"
          @download-excel="downloadExcelMain"
        />
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

const mainGridAdd = () => {
  const mainAddRow = {
    status: 'N',
  }
  mainRowData.value.unshift(mainAddRow)
}

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
  { field: 'gridNm', headerName: '부서이름(코드)', width: 200 },
  { field: 'deptCd', headerName: '부서코드', width: 100, hide: true },
  { field: 'upDeptCd', headerName: '상위부서코드', width: 100, hide: true },
  { field: 'eptGrpFgCd', headerName: '부서그룹구분코드', width: 100, hide: true },
  { field: 'deptTypCd', headerName: '부서유형코드', width: 100, hide: true },
  { field: 'univCd', headerName: '대학코드', width: 100, hide: true },
  { field: 'psustCd', headerName: '학과코드', width: 100, hide: true },
  { field: 'deptNm', headerName: '부서이름', width: 100, hide: true },
  { field: 'deptAbbnm', headerName: '부서약명', width: 100, hide: true },
  { field: 'shafFormaYn', headerName: '학사조직여부', width: 100, hide: true },
  { field: 'danFg', headerName: '주야구분', width: 100, hide: true },
  { field: 'frDt', headerName: '시작일자', width: 100, hide: true },
  { field: 'blnDt', headerName: '폐지일자', width: 100, hide: true },
  { field: 'ablnExpcDt', headerName: '폐지예정일자', width: 100, hide: true },
  { field: 'prnOrd', headerName: '출력순서', width: 100, hide: true },
  { field: 'cyberFormaYn', headerName: '가상조직여부', width: 100, hide: true },
  { field: 'useYn', headerName: '사용여부', width: 100, hide: true },
  { field: 'flDeptYn', headerName: '최종부서여부', width: 100, hide: true },
  { field: 'remk', headerName: '메모', width: 100, hide: true },
  { field: 'campFgCd', headerName: '캠퍼스구분코드 ', width: 100, hide: true },
  { field: 'lastModfIpBreuFg', headerName: '', width: 100, hide: true },
  { field: 'formaTyp', headerName: '조직유형', width: 100, hide: true },
  { field: 'deptEngNm', headerName: '부서영어명', width: 100, hide: true },
  { field: 'deptEngAbbNm', headerName: '부서영어약어명', width: 100, hide: true },
  { field: 'deptChnNm', headerName: '부서중문명', width: 100, hide: true },
  { field: 'deptChnAbbNm', headerName: ' 부서중문약어명', width: 100, hide: true },
  { field: 'deptId', headerName: '부서아이디', width: 100, hide: true },
  { field: 'colgNm', headerName: '대학(처)', width: 100, hide: true },
  { field: 'sustTeamNm', headerName: '학부(팀)', width: 100, hide: true },
  { field: 'certDeptNm', headerName: '증명서부서명', width: 100, hide: true },
  { field: 'daytmSustCd', headerName: '주간 학과코드', width: 100, hide: true },
  { field: 'infoAnncPart', headerName: '정보공시계열', width: 100, hide: true },
  { field: 'shafDegr', headerName: '학사학위', width: 100, hide: true },
  { field: 'masterDegr', headerName: '석사학위', width: 100, hide: true },
  { field: 'phdDegr', headerName: '박사학위', width: 100, hide: true },
  { field: 'connMj', headerName: '연계전공', width: 100, hide: true },
  { field: 'achvPart', headerName: '업적계열', width: 100, hide: true },
  { field: 'phdDegrMjClsf', headerName: '', width: 100, hide: true },
  { field: 'connMjAchvPart', headerName: '', width: 100, hide: true },
  { field: 'regPart', headerName: '', width: 100, hide: true },
  { field: 'rdtDegrIdcdPrnNm', headerName: '졸업학위증출력명', width: 100, hide: true },
  { field: 'achvScrnUniv', headerName: '업적심사대학', width: 100, hide: true },
  { field: 'grscClsf', headerName: '대학원분류', width: 100, hide: true },
  { field: 'sustNm', headerName: '학부(과)', width: 100, hide: true },
  { field: 'gwDeptConnYn', headerName: '그룹웨어부서연계여부', width: 100, hide: true },
  { field: 'sprfGwDeptCd', headerName: '교원그룹부서연계코드', width: 100, hide: true },
  { field: 'empGwDeptCd', headerName: '직원그룹웨어부서연계코드', width: 100, hide: true },
  { field: 'deptSortSeq', headerName: '부서정렬순서', width: 100, hide: true },
  { field: 'subChancMissCd', headerName: '부총장보직코드', width: 100, hide: true },
  { field: 'breuFg', headerName: '기관구분', width: 100, hide: true },
  { field: 'mjClsf', headerName: '전공분류', width: 100, hide: true },
]

let deptDetaInfo = ref({
  gridNm: '',
  deptCd: '',
  upDeptCd: '',
  eptGrpFgCd: '',
  deptTypCd: '',
  univCd: '',
  psustCd: '',
  deptNm: '',
  deptAbbnm: '',
  shafFormaYn: '',
  danFg: '',
  frDt: '',
  blnDt: '',
  ablnExpcDt: '',
  prnOrd: '',
  cyberFormaYn: '',
  useYn: '',
  flDeptYn: '',
  remk: '',
  campFgCd: '',
  lastModfIpBreuFg: '',
  formaTyp: '',
  deptEngNm: '',
  deptEngAbbNm: '',
  deptChnNm: '',
  deptChnAbbNm: '',
  deptId: '',
  colgNm: '',
  sustTeamNm: '',
  certDeptNm: '',
  daytmSustCd: '',
  infoAnncPart: '',
  shafDegr: '',
  masterDegr: '',
  phdDegr: '',
  connMj: '',
  achvPart: '',
  phdDegrMjClsf: '',
  connMjAchvPart: '',
  regPart: '',
  rdtDegrIdcdPrnNm: '',
  achvScrnUniv: '',
  grscClsf: '',
  sustNm: '',
  gwDeptConnYn: '',
  sprfGwDeptCd: '',
  empGwDeptCd: '',
  deptSortSeq: '',
  subChancMissCd: '',
  breuFg: '',
  mjClsf: '',
})

const onGridReady = (params) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
  console.log('gridApi : ', gridApi.value)
  console.log('columnApi : ' + columnApi.value)
}

const onCellClicked = (params) => {
  console.log('cell click : ', params.data)
  deptDetaInfo.value = params.data
}

let param = ref({
  deptNm: '',
  deptCd: '',
  deptId: '',
  useYn: '',
  breuFg: '',
  flDeptYn: '',
  shafFormaYn: '',
})

const search = () => {
  mainGridCall(param)
}

const mainGridCall = (param) => {
  console.log('param : ', param)

  const obj = Object.assign({}, param.value)
  console.log('obj : ', obj)

  axios
    .post('/restApi/prj/com/deptCdMngList.do', obj)
    .then((restApi) => {
      console.log('restApi : ', restApi)
      if (restApi.status === 200) {
        mainRowData.value = restApi.data.deptList
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
