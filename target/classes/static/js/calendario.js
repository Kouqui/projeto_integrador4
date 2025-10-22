// Script para controlar o modal de eventos
const addEventBtn = document.getElementById('add-event-btn');
const eventModal = document.getElementById('event-modal');
const closeModalBtn = document.getElementById('close-modal-btn');
const cancelBtn = document.getElementById('cancel-btn');

addEventBtn.addEventListener('click', () => {
    eventModal.classList.add('visible');
});

const closeModal = () => {
    eventModal.classList.remove('visible');
}

closeModalBtn.addEventListener('click', closeModal);
cancelBtn.addEventListener('click', closeModal);

eventModal.addEventListener('click', (event) => {
    if (event.target === eventModal) {
        closeModal();
    }
});