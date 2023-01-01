import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入新字体
import './assets/font/font.css'
//引入ElementUI-plus(更适用Vue3）
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'

createApp(App).use(store).use(router).mount('#app')
App.use(ElementPlus)