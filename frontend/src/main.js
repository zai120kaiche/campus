import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'

//引入全局样式
import './assets/css/global.css'
//引入新字体
import './assets/font/font.css'
//引入ElementUI-plus和ElementUI
import ElementPlus from 'element-plus'
import 'element-plus/dist/index.css'
//引入axios
import axios from 'axios'
import "./axios"
//滑动验证码
import SlideVerify from 'vue-monoplasty-slide-verify';

const app = createApp(App)
app.config.globalProperties.$axios=axios

app.use(ElementPlus)
app.use(SlideVerify)
app.use(router).use(store).mount("#app")