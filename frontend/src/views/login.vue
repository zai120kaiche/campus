<template>
  <el-container class="page_back page_this">
    <el-header style="margin-top: 5%; margin-left: 10%">
      <el-row>
        <el-col :span="12">
          <el-row>
            <el-col :span="4"><el-image style="width: 80%; height: 80%; z-index: 1; background-color: transparent" :src="require('../assets/images/logo.png')"
                                        :fit="'fill'"></el-image></el-col>
          </el-row>
        </el-col>

      </el-row>
    </el-header>
    <el-container style="margin-left: 10%; margin-right: 7%">
      <el-aside width="58%">


        <el-image style="width: 100%; height: 90%; z-index: -1;" :src="require('../assets/images/login_back.png')"
                  :fit="'fill'"/>

      </el-aside>
      <el-main>
        <el-card v-if="loginOrRegister" style="height: 100%; ">
          <el-menu
              :default-active="activeIndex"
              class="el-menu-demo"
              mode="horizontal"
              @select="handleSelect"

              active-text-color="#524d4c"
          >
            <el-menu-item index="1">密码登录</el-menu-item>

            <el-menu-item index="2">短信登录</el-menu-item>
          </el-menu>
          <div v-if="activeIndex == 1">
            <el-row style="margin-top: 3%">
              <el-col :span="6" :offset="2">没有账号？</el-col>
              <el-col :span="4" style="color: deepskyblue" @click="register()">注册</el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 8%; width: 80%" v-model="loginData.phoneOrEmail" placeholder="手机号/邮箱"/>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 5%; width: 80%" v-model="loginData.password" show-password placeholder="密码"/>
              </el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="loginSend">登录</el-button>
              </el-col>
            </el-row>

          </div>
          <div v-else>
            <el-row style="margin-top: 3%">
              <el-col :span="6" :offset="2">没有账号？</el-col>
              <el-col :span="4" style="color: deepskyblue" @click="register()">注册</el-col>
            </el-row>
            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 3%; width: 80%" v-model="sendData.phoneOrEmail" placeholder="手机号/邮箱">
                  <template #prepend>+ 86</template>
                </el-input>
              </el-col>
            </el-row>
            <!--            <el-row style="margin-top: 3%; width: 80%; margin-left: 1%">-->
            <!--              <el-col :offset="2">-->
            <!--                <Slider status="{{status}}"-->
            <!--                        :successFun="smsSendSucc()"-->
            <!--                        :errorFun="smsSendFail()"></Slider>-->
            <!--              </el-col>-->
            <!--            </el-row>-->

            <el-row>
              <el-col :offset="2">
                <el-input style="margin-top: 3%; width: 48%" v-model="checkNumber" placeholder="验证码"/>
                <el-button v-if="sms_send == true" style="margin-top: 3%; width: 29%; margin-left: 3%" color="#4a86e8"
                           @click="sendSms2()">发送验证码
                </el-button>
                <el-button v-else style="margin-top: 3%; width: 29%; margin-left: 3%" color="#4a86e8" disabled>
                  {{ sms_submit }}
                </el-button>
              </el-col>
            </el-row>


            <el-row>
              <el-col :offset="2">
                <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="loginBySms">登录</el-button>
              </el-col>
            </el-row>
          </div>
          <el-row style="margin-top: 4%">
            <el-col :span="12" :offset="2">
              <el-checkbox label="下次自动登录" name="type" style="width: 40%"/>

            </el-col>
            <el-col :span="6" :offset="1">

              <el-button style="color: deepskyblue; border-color: transparent">忘记密码</el-button>
            </el-col>
          </el-row>
          <div style="text-align: center">

            注册登录即代表同意
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="userVisible = true">用户协议</button>

            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="dialogVisible = true">免责声明</button>
            和
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="secretVisible = true">产品隐私政策</button>


          </div>
          <el-row v-on:click="view" style="margin-top: 3%">
            <el-col :offset="17" :span="5">游客登陆</el-col>
            <el-col :span="2" style="margin-top: 1%"><el-icon><DArrowRight /></el-icon></el-col>
          </el-row>



        </el-card>
        <el-card v-else style="height: 100%;">
          <el-row>
            <el-col :offset="1">
              <div style="font-weight: bold; font-size: 20px;">欢迎注册</div>
            </el-col>
          </el-row>
          <el-row style="margin-top: 3%">
            <el-col :span="6" :offset="2">已有账号？</el-col>
            <el-col :span="4" style="color: deepskyblue" @click="login()">登录</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">用户名</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 80%" v-model="registerData.username" placeholder="用户名"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">手机号/邮箱</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 80%" v-model="sendData.phoneOrEmail" placeholder="手机号/邮箱"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">密码</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input show-password style="margin-top: 1%; width: 80%" v-model="registerData.password" placeholder="密码"/>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">请再次输入密码</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input show-password style="margin-top: 1%; width: 80%" v-model="repeatPassword" placeholder="请再次输入密码"/>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="2">验证码</el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-input style="margin-top: 1%; width: 48%" v-model="checkNumber" placeholder="验证码"/>
              <el-button v-if="sms_send == true" style="margin-top: 1%; width: 29%; margin-left: 3%" color="#4a86e8"
                         @click="sendSms()">发送验证码
              </el-button>
              <el-button v-else style="margin-top: 1%; width: 29%; margin-left: 3%" color="#4a86e8" disabled>
                {{ sms_submit }}
              </el-button>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2">
              <el-button style="margin-top: 5%; width: 80%" color="#4a86e8" v-on:click="smsCheck">注册</el-button>
            </el-col>
          </el-row>
          <div style="text-align: center; margin-top: 3%">

            注册登录即代表同意
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="userVisible = true">用户协议</button>

            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="dialogVisible = true">免责声明</button>
            和
            <button style="color: deepskyblue; border-color: transparent; background-color: transparent" @click="secretVisible = true">产品隐私政策</button>

          </div>
          <el-row v-on:click="view" style="margin-top: 3%">
            <el-col :offset="17" :span="5">游客登陆</el-col>
            <el-col :span="2" style="margin-top: 1%"><el-icon><DArrowRight /></el-icon></el-col>
          </el-row>


        </el-card>


      </el-main>
    </el-container>
    <el-footer style="margin-bottom: 5%; margin-left: 10%">
      <el-row>
        <el-col :span="6">
          <el-row>
            <el-col :span="12">智能客服 | <el-icon style="padding-top: 5%"><User /></el-icon></el-col>
            <el-col :span="11" :offset="1">xingyuls@163.com</el-col>
          </el-row>
        </el-col>

      </el-row>
    </el-footer>
  </el-container>
  <el-drawer v-model="userVisible" :direction="rtl">
    <template #header>
      <h4>用户协议</h4>
    </template>
    <template #default>
      <div>
        《用户协议》<br>
        概述<br>
        本用户协议（以下简称“本协议”）适用于“星屿”（以下简称“我们”，）旗下的产品与服务，如果某项产品与服务有单独的服务规则，则该产品的服务规则优先适用。本协议是您（个人或单一实体）与本网站之间就使用本产品达成的具有法律约束力的法律协议。该法律协议包括但不限于本页面的全部条款、第8条的《隐私政策》以及我们针对特定产品部分功能单独发布的政策或服务规则等（若有）。
        请您在使用本网站之前仔细阅读下列条款。您下载、安装或使用产本品或者单击“ 我同意”表明您已经阅读本协议并充分理解、遵守本协议所有条款，包括涉及免除或者限制本网站责任的免责条款、用户权利限制条款、约定争议解决方式等，这些条款均用粗体字标注。如果您不同意本协议的全部或部分内容，请不要下载、安装和使用本网站。

        <br>1. 权利声明
        <br>1.1 知识产权
        拥有“本网站”的所有权和知识产权等全部权利。本网站受中国及其他国家的知识产权法、国际知识产权公约（包括但不限于著作权法、商标法、专利法等）的保护。所有未授予您的权利均被本网站保留，您不可以从本网站上移除本网站的版权标记或其他权利声明。

        <br>1.2 软件所有权保留。
        您确定不享有本软件的所有权，本软件未被出售给用户，本网站保留本软件的所有权。

        <br>2. 授权许可
        <br>2.1 授权许可
        本网站授予您仅出于个人非商业目在移动设备上使用本网站，如果您希望将本网站用于其他非本网站授权的目的或其他商业目的，您必须另行取得本网站的单独书面许可。
        <br>2.2 限制
        除非就某些第三方软件软件有明文规定或适用法律允许，否则您不得在未取得本网站书面许可的情况下修改、翻译、反向汇编、反向工程、反编译本软件的部分或全部。

        <br>3. 用户行为
        <br>3.1 如果您在使用本网站或服务的过程中发布相关用户内容，您需要对自己发布的所有用户内容负责。用户内容是指您发布或以其他方式使用本网站时产生的所有内容（例如：您的信息、声音、图片或其他内容）。您是您的用户内容唯一的责任人，您将承担因您的用户内容违法法律、侵犯第三方权益的所有法律责任。

        <br>3.2 在使用过程中，您将承担因下述行为而产生的全部法律责任，本网站不对您的下述行为承担任何责任：
        破坏宪法所确定的基本原则的；
        危害国家安全、泄露国家秘密、颠覆国家政权、破坏国家统一的；
        损害国家荣誉和利益的；
        煽动民族仇恨、民族歧视，破坏民族团结的；
        破坏国家宗教政策，宣扬邪教和封建迷信的；
        散布谣言，扰乱社会秩序，破坏社会稳定的；
        散布淫秽、色情、赌博、暴力、凶杀、恐怖或者教唆犯罪的；
        侮辱或者诽谤他人，侵害他人合法权益的；
        含有法律、行政法规禁止的其他内容的。

        <br>3.3 您同意不通过本网站从事下列行为：
        发布或分享电脑病毒、蠕虫、恶意代码、故意破坏或改变计算机系统或数据的软件；
        未经授权，收集其他用户的信息或数据，例如非法收集第三人的个人信息侵犯第三人隐私或其他合法民事权益；
        用自动化的方式恶意使用本网站，给服务器造成过度的负担或以其他方式干扰或损害本网站服务器和网络链接；
        在未授权的情况下，尝试访问本网站的服务器数据或通信数据；
        干扰、破坏本网站其他用户的使用；
        未经本网站授权，修改、破解、反编译、反汇编、逆向工程本网站，发布本网站的修改版、破解版等；
        <br>3.4 费用
        我们的大部功能或服务都是是免费的，包括但不限于个人上网或第三方（包括但不限于电信或移动通讯提供商）收取的通讯费、信息费等相关费用。

        <br>4. 功能的调整、改进与升级
        我们可能对网站进行不时地调整、改进和增减，甚至下线我们部分功能，以不断适应我们的运营需要。任何本网站的更新版本或未来版本或者其他变更同样受到本协议约束。

        <br>5. 无担保声明
        <br>5.1 本网站负责人在发布本网站之前，已尽可能对网站进行了详尽的技术测试和功能测试，但鉴于电子设备、操作系统、网络环境的复杂性，本网站及其负责人不能保证本网站会兼容所有用户的电子设备，也无法保证用户在使用本网站过程中能够持续不出现任何技术故障。
        <br>5.2 在法律允许的最大限度内，本网站无法对网站或服务做任何明示、暗示和强制的担保，包括但不限于设备的兼容性；网站一定满足您的需求或期望；或网站将不间断的、及时的、安全的、或无错误的运行。

        <br>5.3  由于网络环境的自由与开放特征，我们的网站或服可能会被第三方擅自修改、破解发布于互联网，建议用户从专业浏览器登陆我们的网站，我们不会对任何非官方网站承担任何责任。

        <br>6. 赔偿
        <br>6.1 赔偿。在你违反本协议或你所提供的信息侵犯第三方合法权益而导致直接或间接损失的情况下，你应当赔偿其他用户、本网站、第三方合作伙伴的所有损失、费用或支出。
        <br>6.2 赔偿程序。可以通知你及时要求赔偿。然而,本网站未能通知不会减轻你的赔偿义务，除了在某种程度上，未能及时通知你给你造成了实质上的损害。
        <br>6.3 额外的责任。你的赔偿义务不是本网站的唯一补救措施，除此之外可能本网站对你依据本协议采取其他补救措施，你的赔偿义务在本协议终止后仍存在。

        <br>7. 不可抗力与责任限制
        <br>7.1 不可抗力：本协议有效期间，如若遭受不可抗力事件，任何一方可暂行中止履行本协议项下的义务直至不可抗力事件的影响消除，并且遭受方无需为此承担违约责任，但应及时将不可抗力事件及时通知对方，并尽最大努力克服该事件，减少损失的扩大。不可抗力指各方不能控制、不可预见或即使预见亦无法避免的事件且该事件足以妨碍、影响或延误任何一方根据本协议履行其全部或部分义务。该事件包括但不限于自然灾害、战争、法律法规变更、政府命令、计算机病毒、黑客攻击或基础电信运营商服务中断等。
        <br>7.2 损害限制：本网站及其分支，和所属的管理人员、董事、合伙人、雇员、承包商给你造成的所有损害赔偿额度均仅限于你使用网站支付的款额。你放弃对特殊、间接、附带或间接损害要求赔偿的权利，包括并不限于利润损失、收入、使用、或数据和应用的损失，即使本网站知道此类损失的可能性。

        <br>8. 个人信息保护
        保护用户跟个人信息安全、维护用户隐私是我们一贯的理念，并且我们贯穿于所有网站或服务的立项、开发和运营过程。为不断优化用户体验，向用户提供更加个性化、智能化的内容与服务，我们会收集您的个人信息与非个人信息。您在使用特定的功能时，可以查看关于该网站的隐私说明以及我们的《隐私政策》，了解关于我们收集信息的内容、使用目的以及如何保护你的信息安全，该《产品隐私说明》和《隐私政策》均构成本协议的一部分。

        <br>9. 其他
        <br>9.1 通知
        所有有关本协议以及隐私政策的疑问、通知、要求或请求,应当用中文写作并发送至本网站负责人邮箱：【xingyusl@163.com】

        <br>9.2 本协议的修改
        由于业务的拓展、调整或法规变化等原因，本网站可能会适时修改本协议至被法律所允许的程度。如果调整会对您的权利与义务造成重大影响，我们会尽可能通过电子邮件、应用内通知等方式告知您。我们建议您定期访查看我们的网站，关注本协议的任何变化。在本协议修改后您继续使用本网站代表您接受修改后的协议内容。
        <br>9.3 适用法律和管辖法院
        执行本协议和所有程序引起的纠纷适用法律为中华人民共和国法律、解释。由本协议引起的所有纠纷由我们附属网站的住所地法院管辖。

      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="userVisible = false">确认</el-button>
      </div>
    </template>
  </el-drawer>
  <el-drawer v-model="dialogVisible" :direction="rtl">
    <template #header>
      <h4>免责声明</h4>
    </template>
    <template #default>
      <div>
        《免责声明》<br>
        访问者在接受本网站服务之前，请务必仔细阅读本条款并同意本声明。访问者访问本网站的行为以及通过各类方式利用本网站的行为，都将被视作是对本声明全部内容的无异议的认可；如有异议，请立即跟本网站协商，并取得本网站的书面同意意见。

        <br>第一条 访问者在从事与本网站相关的所有行为（包括但不限于访问浏览、利用、转载、宣传介绍）时，必须以善意且谨慎的态度行事；访问者不得故意或者过失的损害或者弱化本网站的各类合法权利与利益，不得利用本网站以任何方式直接或者间接的从事违反中国法律、国际公约以及社会公德的行为，且访问者应当恪守下述承诺：
        <br>1、传输和利用信息符合中国法律、国际公约的规定、符合公序良俗；
        <br>2、不将本网站以及与之相关的网络服务用作非法用途以及非正当用途；
        <br>3、不干扰和扰乱本网站以及与之相关的网络服务；
        <br>4、遵守与本网站以及与之相关的网络服务的协议、规定、程序和惯例等。
        <br>第二条 本网站郑重提醒访问者：请在转载、上载或者下载有关作品时务必尊重该作品的版权、著作权；如果您发现有您未署名的作品，请立即和我们联系，我们会在第一时间加上您的署名或作相关处理。
        <br>第三条 除我们另有明确说明或者中国法律有强制性规定外，本网站用户原创的作品，本网站及作者共同享有版权，其他网站及传统媒体如需使用，须取得本网站的书面授权，未经授权严禁转载或用于其它商业用途。
        <br>第四条 本网站帖子内容仅代表作者本人的观点，不代表本网站的观点和看法，与本网站立场无关，相关责任作者自负。
        <br>第五条 本网站有权将在本网站内发表的帖子用于其他用途，包括网站、电子杂志等，作品有附带版权声明者除外。
        <br>第六条 未经过本网站和作者共同同意，其他任何机构不得以任何形式侵犯其作品著作权，包括但不限于：擅自复制、链接、非法使用或转载，或以任何方式建立作品镜像。
        <br>第七条 本网站所刊载的各类形式（包括但不仅限于文字、图片、图表）的作品仅供参考使用，并不代表本网站同意其说法或描述，仅为提供更多信息，也不构成任何投资建议。对于访问者根据本网站提供的信息所做出的一切行为，除非另有明确的书面承诺文件，否则本网站不承担任何形式的责任。
        <br>第八条 当本网站以链接形式推荐其他网站内容时，本网站并不对这些网站或资源的可用性负责，且不保证从这些网站获取的任何内容、产品、服务或其他材料的真实性、合法性，对于任何因使用或信赖从此类网站或资源上获取的内容、产品、服务或其他材料而造成（或声称造成）的任何直接或间接损失，本网站均不承担任何责任。
        <br>第九条 访问者在本网站注册时提供的一些个人资料，本网站除您本人同意及第十条规定外不会将用户的任何资料以任何方式泄露给任何一方。
        <br>第十条 当政府部门、司法机关等依照法定程序要求本网站披露个人资料时，本网站将根据执法单位之要求或为公共安全之目的提供个人资料。在此情况下之任何披露，本网站概不负责。
        <br>第十一条 由于用户将个人密码告知他人或与他人共享注册账户，由此导致的任何个人资料泄露以及商品信息，本网站不任何责任。
        <br>第十二条 本网站易物部分涉及财产交易，如有用户因个人原因泄露信息，私自进行交易出现问题的，凡是超出本网站给予您的安全交易行为以外的交易行为，网站不负任何责任。
        <br>第十三条 以上声明内容的最终解释权归星屿网（http：//ecampus.chat）所有。

      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="dialogVisible = false">确认</el-button>
      </div>
    </template>
  </el-drawer>
  <el-drawer v-model="secretVisible" :direction="rtl">
    <template #header>
      <h4>产品隐私政策</h4>
    </template>
    <template #default>
      <div>
        《产品隐私政策》<br>
        “星屿”网站深知个人信息对用户的重要性，也深知为用户的个人信息提供有效保护是我们业务健康可持续发展的基石。感谢您对我们的网站和服务的使用和信任！我们郑重承诺，我们将按业界成熟的安全标准，采取相应的安全保护措施来保护您的个人信息。
        我们希望通过本隐私政策向您说明，在使用我们的网站和服务时，我们如何收集、使用、存储、分享和转让您的个人信息，以及我们为您提供的访问、更新、删除和保护这些信息的方式。请在使用我们的产品和服务前，务必仔细阅读并了解本隐私政策，在确认充分理解并同意后使用我们的产品或服务。一旦您开始使用我们的产品和服务，即表示您已充分理解并同意本政策，并同意我们按照本隐私政策收集、使用、储存和分享您的相关信息。如对本政策内容有任何疑问、意见或建议，您可通过本隐私政策提供的各种联系方式与我们联系。
        本隐私政策将帮助您了解以下内容：


        <br>一、 功能说明	1
        <br>二、 未成年人保护	5
        <br>三、我们如何保护您的个人信息	6
        <br>四、您管理个人信息的权利	7

        <br>一、功能说明
        我们会出于以下功能或用途来收集、处理用户信息：
        <br>1.账号登录：<br>1）您的手机号码及短信验证码：我们会基于您提供的手机号码向您发送短信验证码，用于验证您的身份。如果您不提供您收到的短信验证码，将导致注册失败，您也将无法注册并继续使用本网页及相关服务。提示您注意，您提供的手机号码将作为您后续使用的账号并作为登录本产品的用户身份凭证。
        <br>2）密码信息：您也可以为您的账号设置登录密码，该信息非必要提供，您有权选择使用手机号码加密码进行登录或者仅通过手机验证码登录。
        <br>2.学生卡号注册：为了核实您的在校生身份，我们还将收集并验证您的校园卡信息，您可以选择前述任意一种方式完成验证。
        如您属于在本网站更新前注册成为未成年人但实际年龄已满18周岁的用户，希望将您注册时填写的年龄修改为您实际年龄，您也需要进行上述实名认证，认证通过后可更改为跟身份证上一致的年龄。
        <br>3.找回密码：当您需要找回密码时，您需要提供您注册时提供的手机号码及接收到的短信验证码，用于核实您的身份。通过核验后，您需要设置新的密码代替原密码以便于您下次选择用户名+密码的方式登录。
        <br>4.学生社区：可以帮助您实现跨学校竞赛组队、招领失物、交流学习、交流情感、游戏交友、体育健身、志愿活动、吐槽悄悄话的发帖需求。便于满足您的大学全面交流、社交需求。同时，当您确认进入学生社区时，必须要遵守本台制定的社区公约，若违反，本平台有权将您的账号进行封锁。特别的，当您制作/编辑、发布内容时，您的头像、昵称、发布的文字、图片、视频、关联链接、位置以及相关评论、互动等信息将被收集并可能被精选于公共页面展示，同时我们会根据您使用的功能类型分别请求您授权位置或者其他部分权限，若您不同意或取消部分权限授权，将无法使用相应功能。
        <br>5.信息内容发布、浏览、点赞、评论和分享功能：您在发布帖子或对他人帖子进行评论时，您需要向我们提供您发布/发送的文本信息、录制/上传的图片、音频、视频，我们会对您提供的上述内容的合法合规性进行审核，并且会将这些内容作为用户投诉举报时的留存证据。如果您不希望提供上述信息，您将无法使用与相关的功能。如果你取消发布或因其他原因未成功上传，我们会立即删除该内容。
        <br>6.失物招领：文字加图片的形式可以为您提供更快速的查找服务。我们会获取的相册图片，便于查找后台查找。
        <br>7.二手易物平台：为帮助您更好交易二手物品或学习资源，我们提供了收闲置、卖闲置的功能。我们会获取您的模糊地理位置，为您推荐距离更近的、更推荐的商品或买家；若您发布商品，我们会获取您的相册，以便您发布您想卖出的商品。特别的，当您制作/编辑、发布内容时，您的头像、昵称、发布的文字、图片、视频、关联链接、位置以及相关评论、互动等信息将被收集并可能被精选于公共页面展示，同时我们会根据您使用的功能类型分别请求您授权位置或者其他部分权限，若您不同意或取消部分权限授权，将无法使用相应功能，
        <br>8.AI绘画：以便为您提供更加更加契合的画作，我们会获取您的相册权限，并生成AI图片。
        <br>9.MBTI功能：以便为您提供更精确的16型测试结果，我们会获取您的试卷作答结果，并生成对应的结果。如果您想结识同类型或其他类型的同伴，我们会根据您的结果匹配对应人选。请您理解，根据适用法律法规的要求，我们会对您发布/发送的文本信息、录制/上传的图片、音频、视频进行内容合法合规性审核，并且会将这些内容用于用户投诉举报时留存的证据。如果您不希望提供上述信息，您将无法使用相应功能。
        <br>10.搜索功能：当您使用本产品与/或服务中的「搜索」功能时，您需要向我们提供您输入的关键字信息，用于为您展示您需要的内容及您搜索的历史记录，您可以选择删除历史搜索记录。如您不希望提供上述信息，请您不要使用搜索功能。
        <br>11.意见反馈：如您有任何关于本产品与/或服务的反馈意见，您可以在星屿中通过“个人信息”- “设置”- “帮助与反馈”-“意见反馈”进行反馈。您需要提供您的意见描述、上传证明照片用于帮助我们知悉您提出的问题并寻找对应的解决方案。为了能及时对您提出的意见进行反馈，您需要向我们提供您的手机号码，以便于我们可以及时联系到您。
        <br>12.礼品兑换和邮寄：您可以根据消费额度换取相应积分，对应积分可以兑换相关礼品。当您接到我们的礼品配送通知时，您需要提供您的收件人姓名、收货地址及联系方式，以便于我们向您寄送相应的礼品。如您拒绝提供此类信息，我们将无法向您寄送礼品。
        如果您在星屿中购买或收到了由其他用户赠送的实物礼品，您需要向我们提供兑换申请码以及收货人姓名、收货地址和手机号码。如您拒绝提供上述信息的，前述功能将无法实现。如果您在收货信息中提供他人的个人信息，请您确保已经获得他人的同意。

        <br>二、未成年人保护
        星屿十分重视并致力于 保护 18周岁以下未成年人（以下简称“未成年人”） 隐私安全 ， 并且无意收集未成年人的个人信息，但是由于生日并不是 本软件用户必须真实提供的信息，在某些情况下，我们无法识别用户的年龄。因此， 除非征得监护人的同意，未成年人请不要向我们提供自己的姓名、联系地址、电话等个人信息。 如您是18周岁以下的未成年用户，在使用我们的网站前 ， 应事先取得您的监护人的书面同意。 若您是未成年 用户 的监护人，当您对您所监护的未成年人的个人信息有相关疑问时，请与我们联系 。
        如您是已满14周岁但未满18周岁的未成年用户，请您确保您阅读本《隐私政策》及确认的行为系在您的监护人的指导下完成且征得您的监护人的同意 ， 确保您使用我们的网站或服务与进行交易时的安全。
        如您是儿童用户（即未满14周岁的未成年人） ，在使用我们的网站或服务前，需征得您的监护人的指导下完成且征得您的监护人的同意 ，并在监护人的指导下使用我们的网站。

        如您在首次进入网站时，点击 “登陆” ，则视为您 同意使用星屿网站，且您已经完全理解并同意本《 隐私政策 》的全部内容，表示您同意我们按照本 《 隐私政策 》 处理（包括收集、使用、存 储 和 共享等） 您的信息。

        <br>三、我们如何保护您的个人信息
        为防止您的信息丢失、未经您同意授权的访问、公开披露、泄露、转让等导致您个人信息的受损，测测平台将采取一切合理、可靠、可行的方案与措施，保障您的个人信息安全。
        <br>1.安全措施：
        我们将以国家安全标准和法律法规的规定收集、使用、存储和传输用户信息，并通过用户协议和隐私政策告知您相关信息的使用目的和范围。同时，我们会对个人信息进行加密技术，以确保数据的保密性与安全性。
        对员工信息接触者签署保密协议，定期进行安全能力与意识的培训。专岗专职，只有授权员工方可访问个人信息，若违反保密协议，将追究员工相关法律责任。
        严格选择合作伙伴，对其进行背景调查，与有知名度且信誉良好的企业合作；与合作伙伴信息接触者签署保密协议，约定泄密的违约责任，并仅提供必要合理的信息。
        成立安全团队，安全团队负责研发和应用安全技术和程序，以确保测测平台及个人信息的安全。我们将对安全团队负责人和信息技术负责人进行背景调查，签署保密协议，约定泄密的法律责任，并持续对其进行安全能力与意识的培训。
        我们建立完善的信息安全管理制度和内部安全事件处置机制等。

        <br>2.保存期限
        您在使用星屿网站及服务期间，我们将持续为您保存您的个人信息。如果您将个人信息修改，我们会保存修改后的信息。
        如果您注销帐户后，我们不会再对您的个人信息进行使用，我们将依据网络安全法等法律法规规定保存您的信息并进行匿名化处理。
        <br>3.安全事件通知
        我们会预先制定互联网安全事件预警方案，及时处置系统漏洞、计算机病毒、网络攻击、网络侵入等安全风险，在发生危害网络安全的事件时，我们会立即启动应急预案，采取相应的补救措施，并按照规定向有关主管部门报告。
        若发生个人信息安全事件，我们将通过您预留的个人信息（包含手机号码、电子邮箱等）及时通知您，并告知您案件进展和影响，我们将联合安全团队、法务部、技术部共同采取积极有效的处理手段，采取补救措施以降低风险。同时，我们将根据有关政府部门及法律法规要求，主动上报安全事件的情况，并可能采取法律手段解决安全事件。
        如果决定更改隐私政策，我们会在本政策中及我们认为适当的位置发布这些更改，以便您了解我们如何收集、使用您的个人信息，哪些人可以访问这些信息，以及在什么情况下我们会透露这些信息。

        <br>四、您管理个人信息的权利
        我们非常重视您对个人信息的关注，并尽全力保护您对于自己个人信息访问、更正、删除以及撤回同意的权利，以使您拥有充分的能力保障您的隐私和安全。 您的权利包括：
        <br>1.访问和更正您的个人信息
        <br>1.1除法律法规规定外，您有权随时访问和更正您的个人信息，具体包括：您可通过【我】 ，访问或者修改您的邮箱信息、密码、用户名、学籍信息；
        <br>1.2删除您的个人信息
        我们在星屿网站上提供以下方式帮助您删除您在星屿上发布的信息。您可通过【个人信息→我的帖子】，删除您在社区中展示的信息；您可通过【个人信息→ 关注】，取消您的关注记录；您可通过【个人信息→我的交易→我发布的】，来删除您发布的商品信息。
        在以下情形中，您可以向我们提出删除个人信息的请求：
        如果我们处理个人信息的行为违反法律法规；
        如果我们收集、使用您的个人信息，却未征得您的同意；
        如果我们处理个人信息的行为违反了与您的约定；
        如果您注销了星屿帐号；
        如果我们终止服务及运营。
        <br>以上删除请求一旦被响应，我们还将同时通知从星屿获得您个人信息的第三方实体（例如使用测测帐号作为第三方登录账号的其他互联网平台），要求其及时删除，除非法律法规另有规定，或这些实体获得您的独立授权。当您从我们的服务中删除信息后，我们可能不会立即从备份系统中删除相应的信息，但会在备份更新时删除这些信息。

      </div>
    </template>
    <template #footer>
      <div style="flex: auto">
        <el-button type="primary" @click="secretVisible = false">确认</el-button>
      </div>
    </template>
  </el-drawer>

</template>

<script>
import Slider from '@/components/Slider.vue'
import {ElMessageBox, ElNotification} from 'element-plus'
export default {
  name: "login",
  components: {
    Slider
  },
  data() {
    return {
      checkNumber: '',
      activeIndex: 1,
      id: '',
      password: '',
      loginData: {
        phoneOrEmail: '',
        password: ''
      },
      phoneOrEmail: '',
      registerData: {
        id: null,
        username: '',
        password: '',
        email: '',
        phone: ''
      },
      sendData: {
        identifier: 0,
        phoneOrEmail: ''
      },
      ruleForm: {
        type: []
      },
      //验证码倒计时
      sms_send: true,
      sms_submit: 30,
      //滑块验证码
      status: false,
      //login by sms
      sliderCheck: false,
      dialogVisible: false,
      secretVisible: false,
      userVisible: false,
      //flag判断注册还是登录
      loginOrRegister: true,
      repeatPassword: ''

    }
  },
  methods: {
    view(){
      this.$router.push('/index/community')
      localStorage.setItem("userId", null)
      localStorage.setItem("userAvatar", null)
    },
    //发送验证码
    sendSms() {
      let _this = this
      if(_this.sendData.phoneOrEmail == '' || _this.registerData.username == ''){
        ElNotification({
          title: 'Error',
          message: '账号不能为空',
          type: 'error',
        })
      } else {
        if(_this.sendData.phoneOrEmail.search("@") != -1){

          _this.$axios.post('/identify/send', _this.sendData).then(res =>{
            ElNotification({
              title: 'Success',
              message: '验证码发送成功',
              type: 'success',
            })
          })
          _this.sms_send = false
          _this.sms_submit = 60
          var times = setInterval(() => {
            _this.sms_submit--; //递减
            if (_this.sms_submit <= 0) {
              // <=0 变成获取按钮
              _this.sms_send = true;
              clearInterval(times);
            }
          }, 1000); //1000毫秒后执行
        } else {
          if(_this.sendData.phoneOrEmail.length != 11){
            ElNotification({
              title: 'Error',
              message: '手机格式不正确',
              type: 'error',
            })
          } else {
            _this.$axios.post('/identify/send', _this.sendData).then(res =>{
              ElNotification({
                title: 'Success',
                message: '验证码发送成功',
                type: 'success',
              })
            })
            _this.sms_send = false
            _this.sms_submit = 60
            var times = setInterval(() => {
              _this.sms_submit--; //递减
              if (_this.sms_submit <= 0) {
                // <=0 变成获取按钮
                _this.sms_send = true;
                clearInterval(times);
              }
            }, 1000); //1000毫秒后执行
          }
        }
      }



    },
    sendSms2() {
      let _this = this

      if(_this.sendData.phoneOrEmail == ''){
        ElNotification({
          title: 'Error',
          message: '账号不能为空',
          type: 'error',
        })
      } else {
        if(_this.sendData.phoneOrEmail.search("@") != -1){

          _this.$axios.post('/identify/send', _this.sendData).then(res =>{
            ElNotification({
              title: 'Success',
              message: '验证码发送成功',
              type: 'success',
            })
          })
          _this.sms_send = false
          _this.sms_submit = 60
          var times = setInterval(() => {
            _this.sms_submit--; //递减
            if (_this.sms_submit <= 0) {
              // <=0 变成获取按钮
              _this.sms_send = true;
              clearInterval(times);
            }
          }, 1000); //1000毫秒后执行
        } else {
          if(_this.sendData.phoneOrEmail.length != 11){
            ElNotification({
              title: 'Error',
              message: '手机格式不正确',
              type: 'error',
            })
          } else {
            _this.$axios.post('/identify/send', _this.sendData).then(res =>{
              ElNotification({
                title: 'Success',
                message: '验证码发送成功',
                type: 'success',
              })
            })
            _this.sms_send = false
            _this.sms_submit = 60
            var times = setInterval(() => {
              _this.sms_submit--; //递减
              if (_this.sms_submit <= 0) {
                // <=0 变成获取按钮
                _this.sms_send = true;
                clearInterval(times);
              }
            }, 1000); //1000毫秒后执行
          }
        }
      }
    },
    smsSendSucc() {
      let _this = this
      _this.sliderCheck = true
      console.log(_this.sliderCheck)
    },
    smsSendFail() {
      let _this = this
      console.log(_this.sliderCheck)
    },
    smsCheck() {
      let _this = this
      _this.sendData.identifier = _this.checkNumber
      _this.$axios.post('/identify/check', _this.sendData).then(res => {
        if(res.data.code == 200){
          _this.registerSend()
        } else {
          ElNotification({
            title: 'Error',
            message: '验证码不正确',
            type: 'error',
          })
        }
      })
    },
    registerSend() {
      let _this = this
      if(_this.sendData.phoneOrEmail.search("@") != -1){
        _this.registerData.email = _this.sendData.phoneOrEmail
      } else {
        _this.registerData.phone = _this.sendData.phoneOrEmail
      }
      if(_this.repeatPassword != _this.registerData.password){
        ElNotification({
          title: 'Error',
          message: '请检查两次密码是否一致',
          type: 'error',
        })
      }else{
        _this.$axios.post('/register', _this.registerData).then(res =>{
          console.log(res.data)
          if(res.data.data == 1){
            ElNotification({
              title: 'Error',
              message: '账号已存在，请直接登录',
              type: 'error',
            })
          } else {
            ElNotification({
              title: 'Success',
              message: '注册成功',
              type: 'success',
            })
          }
          _this.login()
        })
      }

    },
    loginSend() {
      let _this = this
      _this.$axios.post('login', _this.loginData).then(res =>{
        console.log(res.data.data)
        localStorage.setItem("userId", res.data.data.id)
        localStorage.setItem("userAvatar", res.data.data.avatar)
        this.$store.commit('SET_INDEX', 3)
        this.$router.push({name: "user", params: {flag: 4}})
      })
    },
    loginBySms() {
      let _this = this
      _this.sendData.identifier = _this.checkNumber
      _this.$axios.post('/identify/check', _this.sendData).then(res => {
        if(res.data.code == 200){
          _this.loginSendBySms()
        } else {
          ElNotification({
            title: 'Error',
            message: '验证码不正确',
            type: 'error',
          })
        }
      })
    },
    loginSendBySms() {
      let _this = this
      let temp = {
        phoneOrEmail: _this.sendData.phoneOrEmail,
        flag: 1,
        password: 1
      }
      _this.$axios.post('/login', temp).then(res =>{
        console.log(res.data.data)
        localStorage.setItem("userId", res.data.data.id)
        localStorage.setItem("userAvatar", res.data.data.avatar)
        this.$store.commit('SET_INDEX', 3)
        this.$router.push({name: "user", params: {flag: 4}})
      }).catch(res=>{
        ElNotification({
          title: 'Error',
          message: '密码不正确',
          type: 'error',
        })
      })
    },
    handleSelect(key) {

      let _this = this
      _this.activeIndex = key
    },
    handleClose() {
      ElMessageBox.confirm('你确定要关闭对话框吗?')
          .then(() => {
            done()
          })
          .catch(() => {
            // catch error
          })
    },
    //注册
    register() {
      let _this = this
      _this.loginOrRegister = false
    },
    //登录
    login() {
      let _this = this
      _this.loginOrRegister = true
    }
  }
}
</script>

<style scoped>
.page_this {
  background-image: linear-gradient(45deg, rgba(0, 200, 255, 0.4) 0%, rgba(228, 90, 225, 0.1), rgba(239, 239, 239) 100%);
}

.pic_this {
  position: absolute;
  top: 0;
  left: 0;

  z-index: -1;
  border-radius: 5px;
}
</style>