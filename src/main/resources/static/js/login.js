//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788 e KAUÃ KOUQUI UEMURA RA:24027746

// 1. Pega os elementos do HTML que vamos usar
const openModalLink = document.getElementById('open-modal-link');
const contactModal = document.getElementById('contact-modal');
const closeModalButton = document.getElementById('close-modal-button');

// 2. Função para abrir o modal
openModalLink.addEventListener('click', function(event) {
    event.preventDefault(); // Impede o link de fazer a página pular
    contactModal.classList.add('visible');
});

// 3. Função para fechar o modal clicando no botão 'X'
closeModalButton.addEventListener('click', function() {
    contactModal.classList.remove('visible');
});

// 4. (Opcional) Função para fechar o modal clicando no fundo escuro
contactModal.addEventListener('click', function(event) {
    // Se o local clicado (event.target) for o próprio fundo...
    if (event.target === contactModal) {
        contactModal.classList.remove('visible');
    }
});