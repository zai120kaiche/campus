import { createRouter, createWebHistory } from 'vue-router'

const routes = [
  {
    path: '/',
    name: 'login',
    component:() => import('../views/login.vue')
  },{
    path: '/index/community',
    name: 'community',
    component:() => import('../views/community.vue')
  },{
    path: '/index/shop',
    name: 'shop',
    component:() => import('../views/shop.vue')
  },{
    path: '/back/control',
    name: 'control',
    component:() => import('../views/control')
  },{
    path: '/user/:flag',
    name: 'user',
    component:() => import('../views/user')
  },{
    path: '/user/info',
    name: 'userinfo',
    component:() => import('../views/userinfo')
  },{
    path: '/index/community/detail/:communityId',
    name: 'communitydetail',
    component:() => import('../views/communitydetail')
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
