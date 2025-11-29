document.addEventListener('DOMContentLoaded', () => {

    // --- ESTADO DA APLICAÇÃO ---
    let currentViewDate = new Date(); // Começa na data de hoje

    // Lista de Eventos
    let events = [
        {
            id: 1,
            title: "Treino Sub-17",
            date: "2025-10-03",
            type: "TREINO",
            category: "SUB17",
            timeStart: "16:00",
            timeEnd: "18:00",
            location: "CT Fut360",
            description: "Treino tático"
        },
        {
            id: 2,
            title: "Jogo Amistoso",
            date: "2025-10-08",
            type: "JOGO",
            category: "PROFISSIONAL",
            timeStart: "15:30",
            location: "Estádio Municipal",
            opponent: "Visitante FC",
            description: "Amistoso"
        },
        {
            id: 3,
            title: "Reunião",
            date: "2025-10-14",
            type: "REUNIAO",
            category: "GERAL",
            timeStart: "10:00",
            location: "Sala 1",
            description: "Planejamento mensal"
        },
        {
            id: 4,
            title: "Jogo Estadual",
            date: "2025-10-15",
            type: "JOGO",
            category: "PROFISSIONAL",
            timeStart: "20:00",
            location: "Arena Fut360",
            opponent: "Rival Estadual",
            description: "Valendo classificação"
        }
    ];

    // --- ELEMENTOS DO DOM ---
    const grid = document.getElementById('calendar-grid');
    const monthDisplay = document.getElementById('current-month-display');
    const prevBtn = document.getElementById('prev-month-btn');
    const nextBtn = document.getElementById('next-month-btn');

    const filterCategory = document.getElementById('filter-category');
    const filterType = document.getElementById('filter-type');

    // Modal Elements
    const eventModal = document.getElementById('event-modal');
    const closeModalBtn = document.getElementById('close-modal-btn');
    const cancelBtn = document.getElementById('cancel-btn');
    const eventForm = document.getElementById('event-form');
    const addEventBtn = document.getElementById('add-event-btn');
    const deleteBtn = document.getElementById('delete-btn');

    // Inputs do Formulário
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


    // --- FUNÇÃO DE RENDERIZAÇÃO PRINCIPAL ---
    function renderCalendar() {
        const existingDays = grid.querySelectorAll('.calendar-day');
        existingDays.forEach(day => day.remove());

        const year = currentViewDate.getFullYear();
        const month = currentViewDate.getMonth();

        // Atualiza título do Mês
        const monthNames = ["Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"];
        monthDisplay.textContent = `${monthNames[month]} ${year}`;

        // Cálculos de Data
        const firstDayIndex = new Date(year, month, 1).getDay();
        const lastDay = new Date(year, month + 1, 0).getDate();
        const prevLastDay = new Date(year, month, 0).getDate();

        const today = new Date();

        // 1. Dias do Mês Anterior (Cinza)
        for (let i = firstDayIndex; i > 0; i--) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day', 'day-other-month');
            dayDiv.innerHTML = `<span class="day-number">${prevLastDay - i + 1}</span>`;
            grid.appendChild(dayDiv);
        }

        // 2. Dias do Mês Atual
        for (let i = 1; i <= lastDay; i++) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day');

            const currentDayString = `${year}-${String(month + 1).padStart(2, '0')}-${String(i).padStart(2, '0')}`;

            if (i === today.getDate() && month === today.getMonth() && year === today.getFullYear()) {
                dayDiv.classList.add('day-today');
            }

            dayDiv.innerHTML = `<span class="day-number">${i}</span>`;

            // --- FILTRO E EVENTOS ---
            const catVal = filterCategory.value;
            const typeVal = filterType.value;

            const daysEvents = events.filter(ev => {
                const dateMatch = ev.date === currentDayString;
                const catMatch = catVal === 'all' || ev.category === catVal;
                const typeMatch = typeVal === 'all' || ev.type === typeVal;
                return dateMatch && catMatch && typeMatch;
            });

            daysEvents.forEach(ev => {
                const evEl = document.createElement('div');
                evEl.classList.add('event-item');

                // --- CORREÇÃO AQUI: USA O TIPO PARA DEFINIR A COR ---
                const typeClass = 'event-' + ev.type.toLowerCase();

                // Verifica se o tipo existe no CSS, senão usa 'outro'
                if (['jogo', 'reuniao', 'treino'].includes(ev.type.toLowerCase())) {
                    evEl.classList.add(typeClass);
                } else {
                    evEl.classList.add('event-outro');
                }

                evEl.textContent = ev.title;

                // Clique para Editar
                evEl.addEventListener('click', (e) => {
                    e.stopPropagation();
                    openModalEdit(ev);
                });

                dayDiv.appendChild(evEl);
            });

            grid.appendChild(dayDiv);
        }

        // 3. Dias do Próximo Mês
        const totalSlots = firstDayIndex + lastDay;
        const nextDays = 42 - totalSlots;

        for (let i = 1; i <= nextDays; i++) {
            const dayDiv = document.createElement('div');
            dayDiv.classList.add('calendar-day', 'day-other-month');
            dayDiv.innerHTML = `<span class="day-number">${i}</span>`;
            grid.appendChild(dayDiv);
        }
    }

    // --- OUTRAS FUNÇÕES (Mantidas iguais) ---
    function toggleOpponentField() {
        if (inputType.value === 'JOGO') {
            opponentGroup.classList.remove('hidden');
        } else {
            opponentGroup.classList.add('hidden');
        }
    }

    function openModal() {
        eventModal.classList.add('visible');
    }

    function closeModal() {
        eventModal.classList.remove('visible');
    }

    function resetForm() {
        eventForm.reset();
        inputId.value = '';
        document.getElementById('modal-title').textContent = 'Agendar Novo Evento';
        deleteBtn.style.display = 'none';
        inputDate.value = new Date().toISOString().split('T')[0];
        toggleOpponentField();
    }

    function openModalEdit(ev) {
        document.getElementById('modal-title').textContent = 'Editar Evento';
        inputId.value = ev.id;
        inputTitle.value = ev.title;
        inputDate.value = ev.date;
        inputStart.value = ev.timeStart || '';
        inputEnd.value = ev.timeEnd || '';
        inputType.value = ev.type;
        inputCategory.value = ev.category;
        inputLocation.value = ev.location || '';
        inputOpponent.value = ev.opponent || '';
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

    addEventBtn.addEventListener('click', () => {
        resetForm();
        openModal();
    });

    closeModalBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

    eventModal.addEventListener('click', (e) => {
        if (e.target === eventModal) closeModal();
    });

    inputType.addEventListener('change', toggleOpponentField);

    eventForm.addEventListener('submit', (e) => {
        e.preventDefault();

        const idAtual = inputId.value;

        const eventData = {
            id: idAtual ? parseInt(idAtual) : Date.now(),
            title: inputTitle.value,
            date: inputDate.value,
            timeStart: inputStart.value,
            timeEnd: inputEnd.value,
            type: inputType.value,
            category: inputCategory.value,
            location: inputLocation.value,
            opponent: inputOpponent.value,
            description: inputDesc.value
        };

        if (idAtual) {
            const index = events.findIndex(ev => ev.id == idAtual);
            if (index !== -1) events[index] = eventData;
        } else {
            events.push(eventData);
        }

        renderCalendar();
        closeModal();
    });

    deleteBtn.addEventListener('click', () => {
        const idAtual = inputId.value;
        if (idAtual && confirm('Tem certeza que deseja excluir este evento?')) {
            events = events.filter(ev => ev.id != idAtual);
            renderCalendar();
            closeModal();
        }
    });

    renderCalendar();
});