<template>

    <div class="signUp_user">
        <div class="container_signUp_user">
            <h2>Registrarse</h2>

            <form v-on:submit.prevent="processSignUp" >

                <div class="input-contenedor">
                    <i class="fas fa-user icon"></i>
                    <input type="text" v-model="user.username" placeholder="Username">
                </div>
                <br>
                
                <div class="input-contenedor">
                    <i class="fas fa-key icon"></i>
                    <input type="password" v-model="user.password" placeholder="Password">
                </div>
                <br>
                
                <div class="input-contenedor">
                    <i class="fas fa-user-tie icon"></i>
                    <input type="text" v-model="user.name" placeholder="Name">
                </div>
                <br>

                <div class="input-contenedor">
                    <i class="fas fa-envelope icon"></i>
                    <input type="email" v-model="user.email" placeholder="Email">
                </div>
                <br>

                <div class="input-contenedor">
                    <i class="fas fa-dollar-sign icon"></i>
                    <input type="number" v-model="user.balance" placeholder="Initial Balance">
                </div>
                <br>
                <div class="form__button">
                    <button type="submit">Registrarse</button>
                </div>
            </form>
        </div>

    </div>

</template>




<script>
    import gql from "graphql-tag";

    export default {
        name: "SignUp",

        data: function() {
            return {
                user: {
                    username: "",
                    password: "",
                    name: "",
                    email: "",
                    balance: 0,
                },
            };
        },

        methods: {
            processSignUp: async function() {

                await this.$apollo
                    .mutate({
                        mutation: gql`
                            mutation($userInput: SignUpInput!) {
                                signUpUser(userInput: $userInput) {
                                    refresh
                                    access
                                }
                        }
                        `,
                        variables: {
                            userInput: this.user,
                        }, 
                    })
                    .then((result) => {
                        console.log("1 error")
                        let dataLogIn = {
                            username: this.user.username,
                            token_refresh: result.data.signUpUser.refresh,
                            token_access: result.data.signUpUser.access,
                        };
                        this.$emit("completedRegister", dataLogIn);
                    })
                    .catch((error) => {
                        console.log(error)
                        alert("ERROR: Fallo en el registro. Intente de nuevo");
                    });
            },
        },
    }
</script>


<style>

    .signUp_user{
        margin: 0;
        padding: 0%;
        width: 100%;
        display: flex;
        justify-content: center;
        align-items: center;
    }

    .container_signUp_user {
        border: 3px solid  #283747;
        border-radius: 10px;
        margin-top: 100px;
        width: 25%;
        height: 60%;
        display: flex;
        padding: 35px 0 35px 0;
        flex-direction: column;
        justify-content: center;
        align-items: center;
    }

    .signUp_user h2{
        color: #383838;
        padding-bottom: 30px;
        font-size: 30px;
        font-weight: 500;
    }

    .signUp_user form{
        width: 70%;
    }

    .signUp_user button:hover{
        color: #383838;
        font-weight: 500;
        background: rgb(177, 159, 3);
        transform: scale(1.02);
        cursor: pointer;
    }

</style>