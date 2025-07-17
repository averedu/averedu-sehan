<template>
  <div class="search-box">
    <div v-for="rowIdx in rowCount" :key="rowIdx" class="search-row">
      <template v-for="colIdx in 5" :key="colIdx">
        <!-- 1~4번째 컬럼: 조건 아이템 -->
        <div v-if="colIdx < 5 && itemIndex(rowIdx, colIdx) < items.length" class="search-item">
          <label class="search-label" :for="items[itemIndex(rowIdx, colIdx)].name">
            {{ items[itemIndex(rowIdx, colIdx)].label }}
          </label>
          <!-- 텍스트 입력 -->
          <template v-if="items[itemIndex(rowIdx, colIdx)].type === 'text'">
            <input
              :value="modelValue[items[itemIndex(rowIdx, colIdx)].name]"
              @input="updateValue(items[itemIndex(rowIdx, colIdx)].name, $event.target.value)"
              @keyup.enter="onSearch"
              type="text"
              class="search-input"
            />
          </template>
          <!-- 셀렉트 박스 -->
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
          <!-- 기타 타입은 기본적으로 빈 div로 처리, 필요 시 추가 -->
          <template v-else>
            <input
              :value="modelValue[items[itemIndex(rowIdx, colIdx)].name]"
              @input="updateValue(items[itemIndex(rowIdx, colIdx)].name, $event.target.value)"
              type="text"
              class="search-input"
            />
          </template>
        </div>
        <!-- 5번째 컬럼이면서 마지막 행일 때만 조회 버튼 -->
        <div v-else-if="colIdx === 5 && rowIdx === rowCount" class="search-btn-div">
          <button @click="onSearch" type="button" class="search-btn">조회</button>
        </div>
        <!-- 나머지 빈 컬럼 -->
        <div v-else class="search-item" />
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  onSearch: Function,
  items: {
    type: Array,
    required: true,
    // 예시: [{ name: 'loginId', label: '사용자 ID' }, ...]
  },
  modelValue: {
    type: Object,
    required: true,
  },
})

const emit = defineEmits(['update:modelValue'])

const colsPerRow = 4
const rowCount = computed(() => Math.ceil(props.items.length / colsPerRow))
const itemIndex = (rowIdx, colIdx) => (rowIdx - 1) * colsPerRow + (colIdx - 1)

// 양방향 바인딩 핸들러
function updateValue(name, value) {
  emit('update:modelValue', { ...props.modelValue, [name]: value })
}

function handleSelectChange(name, value) {
  updateValue(name, value) // 첫 번째 동작: 값 업데이트
  props.onSearch() // 두 번째 동작: 바로 조회 실행 등
}
</script>

<style scoped></style>
