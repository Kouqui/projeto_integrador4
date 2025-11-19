// Script para renderizar o calendário com eventos do banco
let currentDate = new Date();
let eventos = [];

// Carrega os eventos ao iniciar a página
document.addEventListener('DOMContentLoaded', () => {
    carregarEventos();
});

async function carregarEventos() {
    try {
        const response = await fetch('/api/calendario/eventos');
        eventos = await response.json();
        renderizarCalendario();
    } catch (error) {
        console.error('Erro ao carregar eventos:', error);
        renderizarCalendario();
    }
}

function renderizarCalendario() {
    const calendarGrid = document.querySelector('.calendar-grid');
    const monthYearDisplay = document.querySelector('.calendar-nav h3');

    // Atualiza o mês/ano exibido
    const meses = ['Janeiro', 'Fevereiro', 'Março', 'Abril', 'Maio', 'Junho',
                   'Julho', 'Agosto', 'Setembro', 'Outubro', 'Novembro', 'Dezembro'];
    monthYearDisplay.textContent = `${meses[currentDate.getMonth()]} ${currentDate.getFullYear()}`;

    // Limpa o calendário (mantém apenas os headers)
    while (calendarGrid.children.length > 7) {
        calendarGrid.removeChild(calendarGrid.lastChild);
    }

    // Calcula primeiro dia do mês e total de dias
    const primeiroDia = new Date(currentDate.getFullYear(), currentDate.getMonth(), 1);
    const ultimoDia = new Date(currentDate.getFullYear(), currentDate.getMonth() + 1, 0);
    const diasNoMes = ultimoDia.getDate();
    const diaSemanaInicio = primeiroDia.getDay();

    // Dias do mês anterior
    const mesAnterior = new Date(currentDate.getFullYear(), currentDate.getMonth(), 0);
    const diasMesAnterior = mesAnterior.getDate();

    for (let i = diaSemanaInicio - 1; i >= 0; i--) {
        const dia = diasMesAnterior - i;
        const dayDiv = criarDiaCalendario(dia, true, false);
        calendarGrid.appendChild(dayDiv);
    }

    // Dias do mês atual
    const hoje = new Date();
    for (let dia = 1; dia <= diasNoMes; dia++) {
        const ehHoje = dia === hoje.getDate() &&
                      currentDate.getMonth() === hoje.getMonth() &&
                      currentDate.getFullYear() === hoje.getFullYear();
        const dayDiv = criarDiaCalendario(dia, false, ehHoje);

        // Adiciona eventos do dia
        const eventosNoDia = buscarEventosDoDia(dia, currentDate.getMonth(), currentDate.getFullYear());
        eventosNoDia.forEach(evento => {
            const eventItem = document.createElement('div');
            eventItem.className = `event-item event-${mapearCategoria(evento.categoria)}`;
            eventItem.textContent = evento.titulo;
            eventItem.title = `${evento.titulo}\n${evento.descricao || ''}\n${evento.local || ''}`;
            dayDiv.appendChild(eventItem);
        });

        calendarGrid.appendChild(dayDiv);
    }

    // Dias do próximo mês
    const diasRestantes = 42 - calendarGrid.children.length + 7;
    for (let dia = 1; dia <= diasRestantes; dia++) {
        const dayDiv = criarDiaCalendario(dia, true, false);
        calendarGrid.appendChild(dayDiv);
    }
}

function criarDiaCalendario(dia, outroMes, ehHoje) {
    const dayDiv = document.createElement('div');
    dayDiv.className = 'calendar-day';
    if (outroMes) dayDiv.classList.add('day-other-month');
    if (ehHoje) dayDiv.classList.add('day-today');

    const dayNumber = document.createElement('span');
    dayNumber.className = 'day-number';
    dayNumber.textContent = dia;
    dayDiv.appendChild(dayNumber);

    return dayDiv;
}

function buscarEventosDoDia(dia, mes, ano) {
    return eventos.filter(evento => {
        const dataEvento = new Date(evento.dataHora);
        return dataEvento.getDate() === dia &&
               dataEvento.getMonth() === mes &&
               dataEvento.getFullYear() === ano;
    });
}

function mapearCategoria(categoria) {
    if (!categoria) return 'general';
    switch (categoria.toLowerCase()) {
        case 'profissional': return 'professional';
        case 'sub-17': return 'sub17';
        case 'sub-20': return 'sub20';
        default: return 'general';
    }
}

// Navegação do calendário
document.querySelector('.calendar-nav button:first-child').addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() - 1);
    renderizarCalendario();
});

document.querySelector('.calendar-nav button:last-child').addEventListener('click', () => {
    currentDate.setMonth(currentDate.getMonth() + 1);
    renderizarCalendario();
});

// Modal de eventos
const addEventBtn = document.getElementById('add-event-btn');
const eventModal = document.getElementById('event-modal');
const closeModalBtn = document.getElementById('close-modal-btn');
const cancelBtn = document.getElementById('cancel-btn');
const eventForm = document.getElementById('event-form');

if (addEventBtn && eventModal) {
    addEventBtn.addEventListener('click', () => {
        eventModal.classList.add('visible');
    });

    const closeModal = () => {
        eventModal.classList.remove('visible');
        eventForm.reset();
    };

    closeModalBtn.addEventListener('click', closeModal);
    cancelBtn.addEventListener('click', closeModal);

    eventModal.addEventListener('click', (event) => {
        if (event.target === eventModal) {
            closeModal();
        }
    });

    eventForm.addEventListener('submit', async (e) => {
        e.preventDefault();

        const formData = {
            titulo: document.getElementById('titulo').value,
            descricao: document.getElementById('descricao').value,
            dataHora: document.getElementById('dataHora').value,
            local: document.getElementById('local').value,
            tipo: document.getElementById('tipo').value,
            categoria: document.getElementById('categoria').value
        };

        try {
            const response = await fetch('/api/calendario/eventos', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(formData)
            });

            if (response.ok) {
                closeModal();
                // Recarrega os eventos e renderiza o calendário
                await carregarEventos();
                alert('Evento criado com sucesso!');
            } else {
                alert('Erro ao criar evento. Tente novamente.');
            }
        } catch (error) {
            console.error('Erro ao salvar evento:', error);
            alert('Erro ao criar evento. Tente novamente.');
        }
    });
}