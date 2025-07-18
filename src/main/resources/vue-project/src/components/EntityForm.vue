<template>
  <div class="entity-form">
    <div :class="gridClass">
      <template v-for="item in items" :key="item.key">
        <div class="flex items-center gap-2">
          <label
            class="w-28 shrink-0 pr-2 text-base font-medium break-words pre-line"
            v-html="item.label.replace(/ /g, '<br>')"
          >
          </label>
          <template v-if="item.type === 'text' || item.type === 'date'">
            <input
              :value="model[item.key]"
              @input="updateField(item.key, $event.target.value)"
              :type="item.type"
              class="bg-gray-50 border border-gray-300 text-gray-900 w-full px-2 py-1 rounded"
            />
          </template>
          <template v-else-if="item.type === 'select'">
            <select
              :value="model[item.key]"
              @change="updateField(item.key, $event.target.value)"
              class="bg-gray-50 border border-gray-300 text-gray-900 w-full px-2 py-1 rounded"
            >
              <option v-for="opt in item.options" :key="opt.value" :value="opt.value">
                {{ opt.label }}
              </option>
            </select>
          </template>
          <template v-else-if="item.type === 'checkbox'">
            <input
              type="checkbox"
              :checked="model[item.key]"
              @change="updateField(item.key, $event.target.checked)"
              class="mr-2"
            />
          </template>
        </div>
      </template>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue'

const props = defineProps({
  model: { type: Object, required: true },
  items: { type: Array, required: true },
  columns: { type: Number, default: 3 },
})
const emit = defineEmits(['update:model'])

function updateField(key, value) {
  emit('update:model', { ...props.model, [key]: value })
}

const gridClass = computed(() => {
  switch (props.columns) {
    case 1:
      return 'grid grid-cols-1 md:grid-cols-1 gap-4'
    case 2:
      return 'grid grid-cols-1 md:grid-cols-2 gap-4'
    case 3:
      return 'grid grid-cols-1 md:grid-cols-3 gap-4'
    case 4:
      return 'grid grid-cols-1 md:grid-cols-4 gap-4'
    default:
      return 'grid grid-cols-1 md:grid-cols-3 gap-4'
  }
})
</script>
