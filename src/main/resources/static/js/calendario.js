//CRIAÇÃO PELO AUTOR: KAUÊ FARIAS LOURENÇO RA:24788788

document.addEventListener('DOMContentLoaded', () => {

    // --- ESTADO DA APLICAÇÃO ---
    let currentViewDate = new Date();
    let events = [];

    // --- ELEMENTOS DO DOM ---
    const grid = document.getElementById('calendar-grid');
    const monthDisplay = document.getElementById('current-month-display');
    const prevBtn = document.getElementById('prev-month-btn');
    const nextBtn = document.getElementById('next-month-btn');
    const filterCategory = document.getElementById('filter-category');
    const filterType = document.getElementById('filter-type');

    // Modais
    const eventModal = document.getElementById('event-modal');
    const deleteModal = document.getElementById('delete-modal'); // NOVO

    // Botões de fechar e ação
    const closeModalBtn = document.getElementById('close-modal-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const eventForm = document.getElementById('event-form');
    const addEventBtn = document.getElementById('add-event-btn');
    const deleteBtn = document.getElementById('delete-btn');

    // Botões do Modal de Confirmação (NOVO)
    const confirmDeleteBtn = document.getElementById('confirm-delete-btn');
    const cancelDeleteBtn = document.getElementById('cancel-delete-btn');

    // Inputs
    const inputId = document.getElementById('event-id');
    const inputTitle = document.getElementById('event-title');
    const inputDate = document.getElementById('event-date');
    const inputStart = document.getElementById('event-time-start');
    const inputEnd = document.getElementById('event-time-end');
    const inputType = document.getElementById('event-type');
    const inputCategory = document.getElementById('event-category');
    const inputLocation = document.getElementById('event-location');
    const inputOpponent = document.getElementById('event-opponent');
    const inputDesc = document.getElementById('event-description');
    const opponentGroup = document.getElementById('opponent-group');

    // --- FUNÇÃO PARA BUSCAR EVENTOS ---
    async function fetchEvents() {
        try {
            const response = await fetch('/api/eventos');
            if (response.ok) {
                events = await response.json();
                renderCalendar();
            }
        } catch (error) {
            console.error("Erro de conexão:", error);
        }
    }

    // --- RENDERIZAÇÃO ---
    function renderCalendar() {
        const existingDays = grid.querySelectorAll('.calendar-day');
        existingDays.forEach(day => day.remove());

        const year = currentViewDate.getFullYear();
        const month = currentViewDate.getMonth();

        const monthNames = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
        monthDisplay.textContent = `${monthNames[month]} ${year}`;

        const firstDayIndex = new Date(year, month, 1).getDay();
        const lastDay = new Date(year, month + 1, 0).getDate();
        const prevLastDay = new Date(year, month, 0).getDate();
        const today = new Date();

        for (let i = firstDayIndex; i > 0; i--) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day', 'day-other-month');
            dayDiv.innerHTML = `<span class="day-number">${prevLastDay - i + 1}</span>`;
            grid.appendChild(dayDiv);
        }

        for (let i = 1; i <= lastDay; i++) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day');
            const currentDayString = `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;

            if (i === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                dayDiv.classList.add('day-today');
            }
            dayDiv.innerHTML = `<span class="day-number">${i}</span>`;

            const catVal = filterCategory.value;
            const typeVal = filterType.value;

            const daysEvents = events.filter(ev => {
                const dateMatch = ev.dataEvento === currentDayString;
                const catMatch = catVal === 'all' || ev.categoria === catVal;
                const typeMatch = typeVal === 'all' || ev.tipo === typeVal;
                return dateMatch && catMatch && typeMatch;
            });

            daysEvents.forEach(ev => {
                const evEl = document.createElement('div');
                evEl.classList.add('event-item');

                const typeClass = 'event-' + ev.tipo.toLowerCase();
                if (['jogo', 'reuniao', 'treino'].includes(ev.tipo.toLowerCase())) {
                    evEl.classList.add(typeClass);
                } else {
                    evEl.classList.add('event-outro');
                }

                evEl.textContent = ev.titulo;

                evEl.addEventListener('click', (e) => {
                    e.stopPropagation();
                    openModalEdit(ev);
                });

                dayDiv.appendChild(evEl);
            });

            grid.appendChild(dayDiv);
        }

        const totalSlots = firstDayIndex + lastDay;
        const nextDays = 42 - totalSlots;
        for (let i = 1; i <= nextDays; i++) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day', 'day-other-month');
            dayDiv.innerHTML = `<span class="day-number">${i}</span>`;
            grid.appendChild(dayDiv);
        }
    }

    // --- FUNÇÕES AUXILIARES ---
    function toggleOpponentField() {
        if (inputType.value === 'JOGO') {
            opponentGroup.classList.remove('hidden');
        } else {
            opponentGroup.classList.add('hidden');
        }
    }

    function openModal() { eventModal.classList.add('visible'); }
    function closeModal() { eventModal.classList.remove('visible'); }

    function resetForm() {
        eventForm.reset();
        inputId.value = '';
        document.getElementById('modal-title').textContent = 'Agendar Novo Evento';
        deleteBtn.style.display = 'none';

        const now = new Date();
        const todayString = now.toISOString().split('T')[0];
        inputDate.value = todayString;
        inputDate.setAttribute('min', todayString);

        toggleOpponentField();
    }

    function openModalEdit(ev) {
        document.getElementById('modal-title').textContent = 'Editar Evento';
        inputDate.removeAttribute('min');

        inputId.value = ev.id;
        inputTitle.value = ev.titulo;
        inputDate.value = ev.dataEvento;
        inputStart.value = ev.horaInicio || '';
        inputEnd.value = ev.horaFim || '';
        inputType.value = ev.tipo;
        inputCategory.value = ev.categoria;
        inputLocation.value = ev.local || '';
        inputOpponent.value = ev.adversario || '';
        inputDesc.value = ev.description || '';

        deleteBtn.style.display = 'block';
        toggleOpponentField();
        openModal();
    }

    // --- LISTENERS ---
    prevBtn.addEventListener('click', () => {
        currentViewDate.setMonth(currentViewDate.getMonth() - 1);
        renderCalendar();
    });
    nextBtn.addEventListener('click', () => {
        currentViewDate.setMonth(currentViewDate.getMonth() + 1);
        renderCalendar();
    });
    filterCategory.addEventListener('change', renderCalendar);
    filterType.addEventListener('change', renderCalendar);
    addEventBtn.addEventListener('click', () => { resetForm(); openModal(); });
    closeModalBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);
    eventModal.addEventListener('click', (e) => { if (e.target === eventModal) closeModal(); });
    inputType.addEventListener('change', toggleOpponentField);


    // --- SALVAR ---
    eventForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        if (!inputId.value) {
            const selectedDate = new Date(inputDate.value + 'T00:00:00');
            const today = new Date();
            today.setHours(0, 0, 0, 0);
            if (selectedDate < today) {
                alert("Não é possível agendar eventos em datas passadas.");
                return;
            }
        }

        const eventData = {
            id: inputId.value ? parseInt(inputId.value) : null,
            titulo: inputTitle.value,
            dataEvento: inputDate.value,
            horaInicio: inputStart.value || null,
            horaFim: inputEnd.value || null,
            tipo: inputType.value,
            categoria: inputCategory.value,
            local: inputLocation.value,
            adversario: inputOpponent.value,
            descricao: inputDesc.value
        };

        try {
            const response = await fetch('/api/eventos', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify(eventData)
            });

            if (response.ok) {
                fetchEvents();
                closeModal();
            } else {
                alert("Erro ao salvar evento.");
            }
        } catch (error) {
            console.error("Erro ao salvar:", error);
        }
    });

    // --- LÓGICA DO MODAL DE EXCLUSÃO (NOVA) ---

    // 1. Quando clicar em "Excluir" no formulário, ABRE o modalzinho
    deleteBtn.addEventListener('click', () => {
        deleteModal.classList.add('visible');
    });

    // 2. Botão "Cancelar" do modalzinho
    cancelDeleteBtn.addEventListener('click', () => {
        deleteModal.classList.remove('visible');
    });

    // 3. Botão "Sim, Excluir" do modalzinho (Faz o DELETE real)
    confirmDeleteBtn.addEventListener('click', async () => {
        const idAtual = inputId.value;
        if (idAtual) {
            try {
                const response = await fetch(`/api/eventos/${idAtual}`, {
                    method: 'DELETE'
                });

                if (response.ok) {
                    fetchEvents(); // Recarrega calendário
                    deleteModal.classList.remove('visible'); // Fecha modalzinho
                    closeModal(); // Fecha modal grande
                } else {
                    alert("Erro ao excluir.");
                }
            } catch (error) {
                console.error("Erro ao excluir:", error);
            }
        }
    });

    // Inicialização
    fetchEvents();
});