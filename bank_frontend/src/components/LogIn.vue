<template>

    <div class="logIn_user">
        <div class="container_logIn_user">
            <h2>Iniciar sesión</h2>

            <form v-on:submit.prevent="processLogInUser" class="form">
                <div class="input-contenedor">
                    <i class="fas fa-envelope icon"></i>
                    <input type="text" v-model="user.username" placeholder="Usuario">
                </div>
                <br>
                <div class="input-contenedor">
                    <i class="fas fa-key icon"></i>
                    <input type="password" v-model="user.password" placeholder="Contraseña">
                </div>
                <br>
                <div class="form__button">
                    <button type="submit">Iniciar Sesión</button>
                </div>
            </form>
        </div>

    </div>

</template>


<script>
    import gql from "graphql-tag";

    export default {
        name: "LogIn",

        data: function() {
            return {
                user: {
                    username: "",
                    password: "",
                },
            };
        },

        methods: {
            processLogInUser: async function() {
                await this.$apollo
                    .mutate({
                        mutation: gql`
                            mutation LogIn($credentials: CredentialsInput!) {
                                logIn(credentials: $credentials) {
                                    refresh
                                    access
                                }
                            }
                        `,
                        variables: {
                            credentials: this.user,
                        },
                    })
                    .then((result) => {
                        let dataLogIn = {
                            username: this.user.username,
                            token_access: result.data.logIn.access,
                            token_refresh: result.data.logIn.refresh,
                        };

                        this.$emit("completedLogIn", dataLogIn);
                    })
                    .catch((error) => {
                        swal("Credenciales Incorrectas.","","error");
                    });
            },
        },
    }
</script>


<style>

    .logIn_user{
        margin: 0;
        padding: 0%;
        height: 100%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container_logIn_user {
        border: 3px solid  #383838;
        border-radius: 10px;
        width: 25%;
        height: 60%;
        box-shadow: 0px 15px 20px rgba(0,0,0,0.1);
        display: flex;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .logIn_user h2{
        color: #383838;
        padding-top: 40px;
        font-size: 30px;
        font-weight: 500;

    }

    .form{
        width: 90%;
        margin: auto;
    }
    .form__button{
        width: 90%;
        margin: auto;
    }
    
    input{
        font-size: 20px;
        width: 82%;
        padding: 10px;
        border: none;
    
    }
    .input-contenedor{
        margin: auto;
        display: flex;
        justify-content: center;
        align-items: center;
        border: 1px solid lightgrey;
        width: 90%;
        border-radius: 5px;
        border-bottom-width: 2px;
    }

    .icon{
        min-width: 50px;
        text-align: center;
        color: #999;
    }

    .form__button button{
        width: 100%;
        height: 50px;
        font-size: 17px;
        font-weight: 400;
        color: #E5E7E9;
        background: #383838;
        border: 1px solid #E5E7E9;
        border-radius: 5px;
        padding: 10px 25px;
        margin: 5px 0;
        transition: all 0.3s ease-in-out;
    }

    .logIn_user button:hover{
        color: #383838;
        font-weight: 500;
        background: rgb(177, 159, 3);
        transform: scale(1.02);
        cursor: pointer;
        
    }

</style>