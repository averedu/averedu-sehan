<script setup>
import { ref } from 'vue'
import { useSidebar } from '../composables/useSidebar'
import { useRouter } from 'vue-router'

const dropdownOpen = ref(false)
const { isOpen } = useSidebar()
</script>

<template>
  <header class="flex items-center justify-between px-6 py-4 bg-white border-b-4 border-indigo-600">
    <div class="flex items-center">
      <button class="text-gray-500 focus:outline-none lg:hidden" @click="isOpen = true">
        <svg class="w-6 h-6" viewBox="0 0 24 24" fill="none" xmlns="http://www.w3.org/2000/svg">
          <path
            d="M4 6H20M4 12H20M4 18H11"
            stroke="currentColor"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </button>

      <h3 class="text-3xl font-medium text-gray-700">{{ $route.name }}</h3>
    </div>
    <!-- <p class="text-right cursor-pointer">로그아웃</p> -->
    <a href="/logout">로그아웃</a>
  </header>
</template>
