// Pequeno script para controlar a abertura e fechamento do modal
const addTransactionBtn = document.getElementById('add-transaction-btn');
const transactionModal = document.getElementById('transaction-modal');
const closeModalBtn = document.getElementById('close-modal-btn');
const cancelBtn = document.getElementById('cancel-btn');

addTransactionBtn.addEventListener('click', () => {
    transactionModal.classList.add('visible');
});

closeModalBtn.addEventListener('click', () => {
    transactionModal.classList.remove('visible');
});

cancelBtn.addEventListener('click', () => {
    transactionModal.classList.remove('visible');
});

// Opcional: fechar o modal clicando fora dele
transactionModal.addEventListener('click', (event) => {
    if (event.target === transactionModal) {
        transactionModal.classList.remove('visible');
    }
});