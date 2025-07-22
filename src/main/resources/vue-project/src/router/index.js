import { createRouter, createWebHistory } from 'vue-router'
import LoginView from '../views/LoginView.vue'
import MainView from '../views/MainView.vue'
import DeptCdMgmtView from '../views/DeptCdMgmtView.vue'
import UserMngView from '@/views/UserMngView.vue'
import ProgramMgmtView from '../views/ProgramMgmtView.vue'
import MenuMgmtView from '../views/MenuMgmtView.vue'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: '/',
      name: 'login',
      component: LoginView,
    },
    {
      path: '/main',
      name: 'main',
      component: MainView,
      children: [
        {
          path: 'userMng',
          name: '사용자관리',
          component: UserMngView,
        },
        {
          path: 'deptCdMgmt',
          name: '부서코드관리',
          component: DeptCdMgmtView,
        },
        {
          path: 'programMgmt',
          name: '프로그램관리',
          component: ProgramMgmtView,
        },
        {
          path: 'menuMgmt',
          name: '메뉴관리',
          component: MenuMgmtView,
        },
      ],
    },
  ],
})

export default router
