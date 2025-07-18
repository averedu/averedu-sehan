<template>
  <div class="flex flex-col p-6 h-full">
    <SearchBox :autoSearch="false" :onSearch="search" :items="condItems" v-model="param" />
    <div class="section flex flex-row items-stretch min-h-0">
      <!-- 왼쪽 부서목록 영역 -->
      <div id="section-left" class="w-1/3 min-w-[350px] flex flex-col pr-6 h-full">
        <div class="section-header">
          <h2 class="section-title">부서목록</h2>
          <GridButton
            :featureAuth="deptListFeatureAuth"
            @add-row="mainGridAdd()"
            @delete-item="deleteItemMain"
            @save-data="saveDataMain"
            @download-excel="downloadExcelMain"
          />
        </div>
        <div id="mainGrid" class="section-grid">
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

      <!-- 오른쪽 상세/연계/이력 영역 -->
      <div id="section-right" class="w-2/3 flex flex-col min-h-0 h-full overflow-y-auto">
        <div>
          <div class="section-header">
            <h2 class="section-title">부서정보</h2>
          </div>
          <EntityForm :model="param" :items="deptInfoItems" @update:model="deptDetaInfo = $event" />
        </div>

        <div class="pt-6">
          <div class="section-header">
            <h2 class="section-title">부서코드 연계정보</h2>
          </div>
          <EntityForm :model="param" :items="deptLinkItems" @update:model="deptDetaInfo = $event" />
        </div>

        <div class="pt-6 pb-6">
          <div class="section-header">
            <h2 class="section-title">부서이력목록</h2>
            <b class="text-sm font-light">
              * 부서가 통·폐합되거나 명칭 변경 등으로 신규 부서를 등록한 경우<br />
              이력 관리를 위해 부서 이력 목록을 등록해주십시오.
            </b>
            <GridButton
              :featureAuth="deptHistoryFeatureAuth"
              @add-row="subGridAdd()"
              @delete-item="deleteItemMain"
              @save-data="saveDataMain"
              @download-excel="downloadExcelMain"
            />
          </div>
          <div id="subGrid" class="section-grid">
            <ag-grid-vue
              :columnDefs="subColumnDefs"
              :rowData="subRowData"
              :gridOptions="gridOptions"
              style="width: 100%; height: 150px"
            ></ag-grid-vue>
          </div>
        </div>
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
import SearchBox from '@/components/SearchBox.vue'
import GridButton from '@/components/GridButton.vue'
import EntityForm from '@/components/EntityForm.vue'

// AG Grid 모듈 등록 및 글로벌 옵션 제공
ModuleRegistry.registerModules([AllCommunityModule])
provideGlobalGridOptions()

// =======================[공통 코드/셀렉트 옵션 정의]=======================
const useYnOptions = [
  { value: '', label: '전체' },
  { value: 'Y', label: '사용' },
  { value: 'N', label: '미사용' },
]
const ynOptions = [
  { value: '', label: '전체' },
  { value: 'Y', label: '예' },
  { value: 'N', label: '아니오' },
]

// =======================[조회조건 필드 및 파라미터 초기화]=======================
const condItems = [
  { name: 'deptNm', label: '부서명', type: 'text' },
  { name: 'deptCd', label: '부서코드', type: 'text' },
  { name: 'deptId', label: '부서ID', type: 'text' },
  { name: 'useYn', label: '사용여부', type: 'select', options: useYnOptions },
  { name: 'breuFg', label: '기관구분', type: 'select', options: ynOptions },
  { name: 'flDeptYn', label: '최종조직여부', type: 'select', options: ynOptions },
  { name: 'shafFormaYn', label: '학사조직여부', type: 'select', options: ynOptions },
]
const param = ref({
  ...Object.fromEntries(condItems.map((item) => [item.name, ''])),
})

// =======================[폼/그리드용 옵션 및 항목 정의]=======================
const breuFgOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '기관' },
  { value: 'N', label: '미기관' },
]
const formaTypOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '조직' },
  { value: 'N', label: '미조직' },
]
const danFgOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '주간' },
  { value: 'N', label: '야간' },
]
const deptInfoItems = [
  { key: 'deptCd', label: '부서코드', type: 'text' },
  { key: 'upDeptCd', label: '상위부서', type: 'text' },
  { key: 'useYn', label: '사용여부', type: 'checkbox' },
  { key: 'breuFg', label: '기관구분', type: 'select', options: breuFgOptions },
  { key: 'formaTyp', label: '조직유형', type: 'select', options: formaTypOptions },
  { key: 'shafFormaYn', label: '학사조직여부', type: 'checkbox' },
  { key: 'deptNm', label: '부서명', type: 'text' },
  { key: 'deptAbbnm', label: '부서약어명', type: 'text' },
  { key: 'cyberFormaYn', label: '가상조직여부', type: 'checkbox' },
  { key: 'deptEngNm', label: '부서영문명', type: 'text' },
  { key: 'deptEngAbbNm', label: '부서영문 약어명', type: 'text' },
  { key: 'flDeptYn', label: '최종조직여부', type: 'checkbox' },
  { key: 'deptChnNm', label: '부서중문명', type: 'text' },
  { key: 'deptChnAbbNm', label: '부서중문 약어명', type: 'text' },
  { key: 'deptId', label: '부서ID', type: 'text' },
  { key: 'frDt', label: '생성일자', type: 'date' },
  { key: 'colgNm', label: '대학(처)', type: 'text' },
  { key: 'danFg', label: '주야구분', type: 'select', options: danFgOptions },
  { key: 'ablnDt', label: '폐지일자', type: 'date' },
  { key: 'sustTeamNm', label: '학부(팀)', type: 'text' },
  { key: 'deptSortSeq', label: '부서정렬순번', type: 'text' },
  { key: 'ablnExpcDt', label: '폐지예정일자', type: 'date' },
  { key: 'subChancMissCd', label: '부총장 조직코드', type: 'select', options: breuFgOptions },
  { key: 'remk', label: '비고', type: 'text' },
]

const shafDegrOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '학사' },
  { value: 'N', label: '미학사' },
]
const masterDegrOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '석사' },
  { value: 'N', label: '미석사' },
]
const phdDegrOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '박사' },
  { value: 'N', label: '미박사' },
]
const mjClsfOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '전공' },
  { value: 'N', label: '미전공' },
]
const achvPartOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '업적' },
  { value: 'N', label: '미업적' },
]
const achvScrnUnivOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '업적심사대학' },
  { value: 'N', label: '미심사대학' },
]
const grscClsfOptions = [
  { value: '', label: '선택' },
  { value: 'Y', label: '대학원' },
  { value: 'N', label: '미대학원' },
]
const deptLinkItems = [
  { key: 'certDeptNm', label: '증명서부서명', type: 'text' },
  { key: 'daytmSustCd', label: '주간학과코드', type: 'text' },
  { key: 'infoAnncPart', label: '정보공시계열', type: 'select', options: breuFgOptions },
  { key: 'shafDegr', label: '학사학위', type: 'select', options: shafDegrOptions },
  { key: 'masterDegr', label: '석사학위', type: 'select', options: masterDegrOptions },
  { key: 'phdDegr', label: '박사학위', type: 'select', options: phdDegrOptions },
  { key: 'mjClsf', label: '전공분류', type: 'select', options: mjClsfOptions },
  { key: 'connMj', label: '연계전공', type: 'text' },
  { key: 'achvPart', label: '업적계열', type: 'select', options: achvPartOptions },
  { key: 'regPart', label: '등록계열', type: 'text' },
  { key: 'grdtDegrIdcdPrnNm', label: '졸업학위증 출력명', type: 'text' },
  { key: 'lsnYr', label: '수업년한', type: 'text' },
  { key: 'achvScrnUniv', label: '업적심사대학', type: 'select', options: achvScrnUnivOptions },
  { key: 'grscClsf', label: '대학원분류', type: 'select', options: grscClsfOptions },
  { key: 'sustNm', label: '학부(과)', type: 'text' },
  { key: 'gwDeptConnYn', label: '그룹웨어 부서연계여부', type: 'checkbox' },
  { key: 'sprfGwDeptCd', label: '교원그룹웨어 부서코드', type: 'text' },
  { key: 'empGwDeptCd', label: '직원그룹웨어 부서코드', type: 'text' },
]

// =======================[권한/버튼 활성화 설정]=======================
const deptListFeatureAuth = ref({
  add: true,
  delete: true,
  save: true,
  download: true,
})
const deptHistoryFeatureAuth = ref({
  add: true,
  delete: true,
  save: true,
  download: true,
})

// =======================[그리드 데이터 및 컬럼 정의]=======================
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
      } else if (params.value === 'I') {
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

const subRowData = ref([])
const subColumnDefs = [
  {
    field: 'status',
    editable: false,
    headerName: '상태',
    width: 100,
    cellRenderer: (params) => {
      if (params.value === 'D') {
        return '<img src="path_to_x_image.png" alt="삭제" />'
      } else if (params.value === 'U') {
        return '<img src="path_to_u_image.png" alt="변경" />'
      } else if (params.value === 'I') {
        return '<img src="path_to_n_image.png" alt="추가" />'
      }
      return ''
    },
  },
  {
    field: 'subRowNum ',
    headerName: '순번',
    valueGetter: 'node.rowIndex + 1',
    cellEditorParams: { min: 0, max: 100 },
    cellStyle: { textAlign: 'center' },
    width: 100,
  },
  { field: 'bfDeptCd', headerName: '이전부서', width: 100 },
  { field: 'afDeptCd', headerName: '이후부서', width: 100 },
  { field: 'remk', headerName: '비고', width: 200 },
]

// =======================[상세 정보 상태]=======================
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

// =======================[이벤트 핸들러/CRUD 함수]=======================

// AG Grid 준비 완료 시 API 저장
const gridApi = ref()
const columnApi = ref()
const onGridReady = (params) => {
  gridApi.value = params.api
  columnApi.value = params.columnApi
  console.log('gridApi : ', gridApi.value)
  console.log('columnApi : ' + columnApi.value)
}

// 그리드 셀 클릭 시 상세 정보 갱신
const onCellClicked = (params) => {
  console.log('cell click : ', params.data)
  deptDetaInfo.value = params.data
}

// 부서목록 추가
const mainGridAdd = () => {
  const mainAddRow = { status: 'I' }
  mainRowData.value.unshift(mainAddRow)
}

// 부서이력목록 추가
const subGridAdd = () => {
  const subAddRow = { status: 'I' }
  subRowData.value.unshift(subAddRow)
}

// 조회(검색) 실행
const search = () => {
  mainGridCall(param)
}

// 메인 그리드 데이터 조회
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
        subRowData.value = restApi.data.deptHistList
        console.log(mainRowData.value)
      }
    })
    .catch((error) => {
      console.log('mainGrid load error', error)
    })
}
</script>

<style lang="scss" scoped></style>
