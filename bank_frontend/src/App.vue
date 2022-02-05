<template>

  <div id="app" class="app">

    <div class="header">
      <div class="header__logo">
       <img src="../public/logo.png" alt=""> 
       <h1>Gold Bank</h1>
      </div>
      <nav>
        <button v-if="is_auth" v-on:click="loadHome"> Inicio </button>
        <button v-if="is_auth" v-on:click="loadAccount"> Cuenta </button>
        <button v-if="is_auth" v-on:click="loadTransaction"> Transacción </button>
        <button v-if="is_auth" v-on:click="logOut"> Cerrar Sesión </button>
        <button v-if="!is_auth" v-on:click="loadLogIn" > Iniciar Sesión </button>
        <button v-if="!is_auth" v-on:click="loadSignUp" > Registrarse </button>
      </nav>
    </div>
    

    <div class="main-component">
      <router-view  
        v-on:completedLogIn="completedLogIn"
        v-on:completedTransaction="completedTransaction"
        v-on:completedRegister="completedRegister"
        v-on:completedSignUp="completedSignUp"
        v-on:logOut="logOut"
      >
      </router-view>
    </div>
    

    <div class="footer">
      <h2>Gold Bank</h2>
    </div>

  </div>

</template>



<script>

  export default {
    name: 'App',

    data: function(){
        return{
          is_auth: false
        }
    },

    components: {
    },

    methods:{
      verifyAuth: function() {
        this.is_auth = localStorage.getItem("isAuth") || false;
      
        if (this.is_auth == false)
          this.$router.push({ name: "logIn" });
        else
          this.$router.push({ name: "home" });
      },

      loadLogIn: function(){
        this.$router.push({name: "logIn"})
      },

      loadSignUp: function(){
        this.$router.push({name: "signUp"})
      },

      completedLogIn: function(data) {
        localStorage.setItem("isAuth", true);
        localStorage.setItem("username", data.username);
        localStorage.setItem("token_access", data.token_access);
        localStorage.setItem("token_refresh", data.token_refresh);
        swal("Autenticación Exitosa","","success")
        this.verifyAuth();
      },

      completedRegister:function(data){
        localStorage.setItem("isAuth", true);
        localStorage.setItem("username", data.username);
        localStorage.setItem("token_access", data.token_access);
        localStorage.setItem("token_refresh", data.token_refresh);
        swal("Registro Exitoso","","success")
        this.verifyAuth();
      },
      
      completedSignUp: function(data) {
        alert("Registro Exitoso");
        this.completedLogIn(data);
      },

      completedTransaction:function (){
        this.loadAccount();
      },

      loadHome: function() {
        this.$router.push({ name: "home" });
      },

      loadAccount: function () {
        this.$router.push({ name: "account" });
      },

      loadTransaction: function(){
        this.$router.push({ name: "transaction" });
      },


      logOut: function () {
        localStorage.clear();
        this.verifyAuth();
      },
    },

    created: function(){
      this.verifyAuth()
    }

  }
</script>






<style>

  body{
    margin: 0 0 0 0;
  }

  .header__logo{
    display: flex;
    justify-content: center;
    align-items: center;
    width: 30%;
  }

  .header__logo img{
    width: 50px;
    height: 50px;
    margin-right: 10px;
  }

  .header{
    margin: 0%;
    padding: 0;
    width: 100%;
    height: 10vh; 
    min-height: 100px;

    background-color: #383838 ;
    color:#E5E7E9  ;

    display: flex;
    justify-content: space-between;
    align-items: center;
  }

  .header h1{
    width: 30%;
    text-align: center;
  }

  .header nav {
    height: 100%;
    width: 30%;

    display: flex;
    justify-content: space-around;
    align-items: center;

    font-size: 20px;
  }

  .header nav button{
    color: rgb(190, 172, 5);
    background: none;
    border: 1px solid rgb(190, 172, 5);

    border-radius: 5px;
    padding: 10px 20px;
  }

  .header nav button:hover{
    color: rgb(241, 226, 89);
    border: 1px solid rgb(241, 226, 89);
    cursor: pointer;
  }

  .main-component{
    height: 75vh;
    margin: 0%;
    padding: 0%;
    background: #FDFEFE ;
  }

  .footer{
    text-align: center;
    background-color: #383838;
    padding: 30px 40px 40px 40px;
  }

  .footer h2{
    color: rgb(190, 172, 5);
  }

</style>

