import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
//引入新字体
import './assets/font/font.css'
//引入ElementUI-plus和ElementUI
import ElementPlus from 'element-plus'
import ElementUI from 'element-plus'
import 'element-plus/dist/index.css'
import 'element-plus/theme-chalk/index.css'
import locale from 'element-plus/lib/locale/lang/zh-cn'


createApp(App).use(store).use(router).mount('#app')
App.use(ElementUI)
App.use(ElementPlus,{locale})
