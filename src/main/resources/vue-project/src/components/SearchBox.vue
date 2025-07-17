<template>
  <div class="search-box">
    <div v-for="rowIdx in rowCount" :key="rowIdx" class="search-row">
      <template v-for="colIdx in 5" :key="colIdx">
        <!-- 1~4번째 컬럼: 조건 아이템 -->
        <div v-if="colIdx < 5 && itemIndex(rowIdx, colIdx) < items.length" class="search-item">
          <label class="search-label" :for="items[itemIndex(rowIdx, colIdx)].name">
            {{ items[itemIndex(rowIdx, colIdx)].label }}
          </label>
          <!-- 텍스트 입력 필드 -->
          <template v-if="items[itemIndex(rowIdx, colIdx)].type === 'text'">
            <input
              :value="modelValue[items[itemIndex(rowIdx, colIdx)].name]"
              @input="updateValue(items[itemIndex(rowIdx, colIdx)].name, $event.target.value)"
              @keyup.enter="onSearch"
              type="text"
              class="search-input"
            />
          </template>
          <!-- 셀렉트 박스 필드 -->
          <template v-else-if="items[itemIndex(rowIdx, colIdx)].type === 'select'">
            <select
              :value="modelValue[items[itemIndex(rowIdx, colIdx)].name]"
              @change="
                handleSelectChange(items[itemIndex(rowIdx, colIdx)].name, $event.target.value)
              "
              class="search-select"
            >
              <option
                v-for="opt in items[itemIndex(rowIdx, colIdx)].options"
                :key="opt.value"
                :value="opt.value"
              >
                {{ opt.label }}
              </option>
            </select>
          </template>
          <!-- 지원하지 않는 타입 처리(기본적으로 텍스트 입력) -->
          <template v-else>
            <input
              :value="modelValue[items[itemIndex(rowIdx, colIdx)].name]"
              @input="updateValue(items[itemIndex(rowIdx, colIdx)].name, $event.target.value)"
              type="text"
              class="search-input"
            />
          </template>
        </div>
        <!-- 5번째 컬럼이면서 마지막 행일 때만 조회 버튼 표시 -->
        <div v-else-if="colIdx === 5 && rowIdx === rowCount" class="search-btn-div">
          <button @click="onSearch" type="button" class="search-btn">조회</button>
        </div>
        <!-- 나머지 빈 컬럼 처리 -->
        <div v-else class="search-item" />
      </template>
    </div>
  </div>
</template>

<script setup>
// =======================[Vue 및 props 정의]=======================
import { computed } from 'vue'

// 부모에서 전달받는 props 정의
const props = defineProps({
  onSearch: Function, // 조회 함수
  items: {
    type: Array,
    required: true,
    // 예시: [{ name: 'loginId', label: '사용자 ID' }, ...]
  },
  modelValue: {
    type: Object,
    required: true, // v-model로 바인딩되는 값
  },
})

// v-model 양방향 바인딩을 위한 emit 정의
const emit = defineEmits(['update:modelValue'])

// =======================[행/열 계산 및 인덱스 함수]=======================
const colsPerRow = 4 // 한 행에 표시할 컬럼 수
const rowCount = computed(() => Math.ceil(props.items.length / colsPerRow)) // 총 행 수 계산
const itemIndex = (rowIdx, colIdx) => (rowIdx - 1) * colsPerRow + (colIdx - 1) // 아이템 인덱스 계산

// =======================[입력값 변경 핸들러]=======================
// input/select 값 변경 시 부모의 modelValue를 업데이트
function updateValue(name, value) {
  emit('update:modelValue', { ...props.modelValue, [name]: value })
}

// 셀렉트 박스 변경 시 값 업데이트 및 바로 조회 실행
function handleSelectChange(name, value) {
  updateValue(name, value) // 값 업데이트
  props.onSearch() // 조회 함수 실행
}
</script>

<style scoped></style>