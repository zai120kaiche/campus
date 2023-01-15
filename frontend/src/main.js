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
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
//引入axios
import axios from 'axios'
import "./axios"
//滑动验证码
import SlideVerify from 'vue-monoplasty-slide-verify';
//引入动画特效
import 'animate.css/animate.min.css'
const app = createApp(App)
app.config.globalProperties.$axios=axios
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
    app.component(key, component)
}
app.use(ElementPlus)
app.use(SlideVerify)

app.use(router).use(store).mount("#app")