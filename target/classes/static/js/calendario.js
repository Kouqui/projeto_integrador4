// Espera o HTML carregar antes de rodar o script
document.addEventListener('DOMContentLoaded', () => {

    // --- 1. Seleção de Elementos do DOM ---

    // Modal e seus componentes
    const eventModal = document.getElementById('event-modal');
    const modalTitle = document.getElementById('modal-title');
    const closeModalBtn = document.getElementById('close-modal-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const eventForm = document.getElementById('event-form');

    // Botão principal "Agendar Evento"
    const addEventBtn = document.getElementById('add-event-btn');

    // Pega TODOS os itens de evento que já estão no calendário
    const allEventItems = document.querySelectorAll('.event-item');

    // Campos do formulário dentro do modal
    const eventTitleInput = document.getElementById('event-title');
    const eventDateInput = document.getElementById('event-date');
    const eventTimeStartInput = document.getElementById('event-time-start');
    const eventTimeEndInput = document.getElementById('event-time-end');
    const eventTypeInput = document.getElementById('event-type');
    const eventCategoryInput = document.getElementById('event-category');
    const eventLocationInput = document.getElementById('event-location');
    const eventDescriptionInput = document.getElementById('event-description');

    // Campos condicionais
    const opponentGroup = document.getElementById('opponent-group');
    const eventOpponentInput = document.getElementById('event-opponent');

    // Botões de Ação
    const deleteBtn = document.getElementById('delete-btn');

    // --- 2. Funções Auxiliares ---

    const openModal = () => {
        eventModal.classList.add('visible');
    }

    const closeModal = () => {
        eventModal.classList.remove('visible');
    }

    // Função para mostrar/esconder o campo "Adversário"
    const toggleOpponentField = () => {
        if (eventTypeInput.value === 'JOGO') {
            opponentGroup.classList.remove('hidden');
        } else {
            opponentGroup.classList.add('hidden');
            eventOpponentInput.value = ''; // Limpa o campo se não for um jogo
        }
    }

    // Função para limpar e resetar o formulário para "Novo Evento"
    const resetFormForNewEvent = () => {
        modalTitle.textContent = 'Agendar Novo Evento'; // Muda o título
        eventForm.reset(); // Limpa todos os campos do formulário

        // Pega a data de hoje no formato YYYY-MM-DD
        const today = new Date().toISOString().split('T')[0];
        eventDateInput.value = today; // Define a data para hoje

        deleteBtn.style.display = 'none'; // Esconde o botão de excluir
        toggleOpponentField(); // Garante que o campo adversário esteja no estado correto
    }

    // --- 3. Lógica para ABRIR MODAL (para Agendar Novo Evento) ---
    addEventBtn.addEventListener('click', () => {
        resetFormForNewEvent(); // Limpa e prepara o formulário
        openModal();
    });

    // --- 4. Lógica para ABRIR MODAL (para Ver/Editar Evento Existente) ---
    allEventItems.forEach(item => {
        item.addEventListener('click', () => {
            // Pega os dados 'data-*' do evento clicado
            const { title, date, timeStart, timeEnd, type, category, location, opponent, description } = item.dataset;

            // Preenche o formulário do modal com os dados
            modalTitle.textContent = 'Detalhes do Evento'; // Muda o título

            eventTitleInput.value = title;
            eventDateInput.value = date;
            eventTimeStartInput.value = timeStart;
            eventTimeEndInput.value = timeEnd;
            eventTypeInput.value = type;
            eventCategoryInput.value = category;
            eventLocationInput.value = location;
            eventOpponentInput.value = opponent;
            eventDescriptionInput.value = description;

            deleteBtn.style.display = 'block'; // Mostra o botão de excluir
            toggleOpponentField(); // Verifica se deve mostrar o campo "Adversário"

            openModal();
        });
    });

    // --- 5. Lógica de Interação do Modal ---

    // "Ouvinte" para o dropdown de "Tipo"
    eventTypeInput.addEventListener('change', toggleOpponentField);

    // Fecha ao clicar no 'X'
    closeModalBtn.addEventListener('click', closeModal);

    // Fecha ao clicar em 'Cancelar'
    cancelBtn.addEventListener('click', closeModal);

    // Fecha ao clicar fora do modal (no fundo escuro)
    eventModal.addEventListener('click', (event) => {
        if (event.target === eventModal) {
            closeModal();
        }
    });

    // Ação do Botão "Excluir"
    deleteBtn.addEventListener('click', () => {
        // Aqui você colocaria a lógica real de exclusão (ex: fetch para o backend)
        if (confirm('Tem certeza que deseja excluir este evento?')) {
            alert('Evento excluído!'); // Apenas para teste
            closeModal();
            // Aqui você também removeria o evento do DOM (da tela)
        }
    });

    // Ação do Botão "Salvar" (Submit do formulário)
    eventForm.addEventListener('submit', (e) => {
        e.preventDefault(); // Impede que a página recarregue

        // Aqui você pegaria todos os dados dos inputs e enviaria para o seu backend

        const TítuloSalvo = eventTitleInput.value;
        alert('Evento "' + TítuloSalvo + '" foi salvo!'); // Apenas para teste

        closeModal();
        // Aqui você atualizaria o calendário no DOM (na tela)
    });

});