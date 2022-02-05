<template>

	<div id="Transaction" class="transaction">
		<div class="container_transaction">
			<h2>Realizar Transacción</h2>

			<form v-on:submit.prevent="processTransaction">

				<div class="input-contenedor">
					<i class="fas fa-share-square icon"></i>
					<input type="text" v-model="createTransaction.usernameDestiny" placeholder="Usuario Destino"/>
				</div>
				<br />

				<div class="input-contenedor">
					<i class="fas fa-dollar-sign icon"></i>
					<input type="number" v-model="createTransaction.value" placeholder="Valor"/>
				</div>

				<br />
				<div class="form__button">
					<button type="submit">Enviar</button>
				</div>

			</form>
		</div>
	</div>
	
</template>

<script>
	import gql from 'graphql-tag';

	export default {
		name: 'Transaction',

		data: function() {
			return {
				createTransaction: {
					usernameOrigin: localStorage.getItem('username'),
					usernameDestiny: '',
					value: '',
				},
			};
		},
		methods: {
			processTransaction: async function() {
				if ( localStorage.getItem('token_access') === null || localStorage.getItem('token_refresh') === null) {
					console.log("entro en el if")
					this.$emit('logOut');
					return;
				}
				localStorage.setItem('token_access', '');
				await this.$apollo.mutate({

						mutation: gql`
							mutation RefreshToken($token: Refresh!) {
  								refreshToken(token: $token) {
    								access
  								}
							}
						`,
						variables: {
							token: {

								refresh: localStorage.getItem('token_refresh'),
							} 
							
						},
					})
					.then((result) => {
						localStorage.setItem('token_access', result.data.refreshToken.access);
					})
					.catch((error) => {
						console.log(error)
						this.$emit('logOut');
						return;
					});

				await this.$apollo.mutate({

						mutation: gql`
							mutation($transaction: TransactionInput!) {
								createTransaction(transaction: $transaction) {
									date
									id
									usernameDestiny
									usernameOrigin
									value
								}
							}
						`,
						variables: {
							transaction: this.createTransaction,
						},
					})
					.then((result) => {
						swal(
							'Transacción Realizada de Manera Correcta','','success'
						);
						this.$emit("completedTransaction")
					})
					.catch((error) => {
						console.log(error);
						if(error.message == "404: Not Found"){
							swal("Destino Incorrecto","","error");
						}else if(error.message == "409: Conflict"){
							swal("Saldo insuficiente","","info")
						}else{
							swal("Error inesperado. Intente de nuevo","","error")
						}
						
					});
			},
		},
	};
</script>

<style>

	.transaction {
		margin: 0;
		padding: 0%;
		height: 100%;
		width: 100%;
		display: flex;
		justify-content: center;
		align-items: center;
	}

	.container_transaction {
		border: 3px solid #383838;
		border-radius: 10px;
		width: 25%;
		height: 60%;
		display: flex;
		flex-direction: column;
		justify-content: center;
		align-items: center;
		box-shadow: 0px 15px 20px rgba(0,0,0,0.1);
	}

	.transaction h2 {
		color: #383838;
		margin-bottom: 70px;
	}

	.transaction form {
		width: 70%;
	}

	.transaction button {
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

	.transaction button:hover {
		color: #383838;
        font-weight: 500;
        background: rgb(177, 159, 3);
        transform: scale(1.02);
        cursor: pointer;
	}

</style>
