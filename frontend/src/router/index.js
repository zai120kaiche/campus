import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'login1',
    component:() => import('../views/login1.vue')
  },
  {
    path: '/register',
    name: 'register',
    component: () => import('../views/register.vue')
  },
  {
    path: '/login2',
    name: 'login2',
    component: () => import('../views/login2.vue')
  },
  {
    path:'/verification',
    name:'verification',
    component: () => import('../views/verification.vue')
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
