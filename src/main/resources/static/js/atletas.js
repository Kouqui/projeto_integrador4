document.addEventListener('DOMContentLoaded', function () {

    // --- LÓGICA DO MODAL DE DETALHES (O QUE QUEBROU) ---
    const athleteCards = document.querySelectorAll('.athlete-card');
    const modal = document.getElementById('athlete-modal');
    const closeModalBtn = document.getElementById('close-modal-btn');

    // Elementos do Modal
    const modalPhoto = document.getElementById('modal-photo');
    const modalName = document.getElementById('modal-name');
    const modalPosition = document.getElementById('modal-position');
    // Aba 1
    const modalFullname = document.getElementById('modal-fullname');
    const modalBirthdate = document.getElementById('modal-birthdate');
    const modalAge = document.getElementById('modal-age');
    const modalNationality = document.getElementById('modal-nationality');
    const modalHeight = document.getElementById('modal-height');
    const modalWeight = document.getElementById('modal-weight');
    const modalFoot = document.getElementById('modal-foot');
    // Aba 2 - Temporada
    const modalGames = document.getElementById('modal-games');
    const modalMinutes = document.getElementById('modal-minutes');
    const modalGoals = document.getElementById('modal-goals');
    const modalAssists = document.getElementById('modal-assists');
    const modalYellow = document.getElementById('modal-yellow');
    const modalRed = document.getElementById('modal-red');
    // Aba 2 - Último Treino (SofaScore)
    const modalTreinoNotaGeral = document.getElementById('modal-treino-nota-geral');
    const modalTreinoNotaTatica = document.getElementById('modal-treino-nota-tatica');
    const modalTreinoNotaTecnica = document.getElementById('modal-treino-nota-tecnica');
    const modalTreinoNotaFisica = document.getElementById('modal-treino-nota-fisica');

    const modalTreinoPasses = document.getElementById('modal-treino-passes');
    const modalTreinoPassesBar = document.getElementById('modal-treino-passes-bar');
    const modalTreinoChutes = document.getElementById('modal-treino-chutes');
    const modalTreinoChutesBar = document.getElementById('modal-treino-chutes-bar');
    const modalTreinoDribles = document.getElementById('modal-treino-dribles');
    const modalTreinoDriblesBar = document.getElementById('modal-treino-dribles-bar');

    const modalTreinoDesarmes = document.getElementById('modal-treino-desarmes');
    const modalTreinoInterceptacoes = document.getElementById('modal-treino-interceptacoes');
    const modalTreinoKm = document.getElementById('modal-treino-km');
    const modalTreinoVelMax = document.getElementById('modal-treino-vel-max');
    const modalTreinoSprints = document.getElementById('modal-treino-sprints');

    // Aba 3
    const modalMeta1 = document.getElementById('modal-meta-1');
    const modalMeta2 = document.getElementById('modal-meta-2');
    const modalObservation = document.getElementById('modal-observation');
    // Aba 4
    const modalSalary = document.getElementById('modal-salary');
    const modalContractStart = document.getElementById('modal-contract-start');
    const modalContractEnd = document.getElementById('modal-contract-end');
    const modalReleaseClause = document.getElementById('modal-release-clause');

    // Função para aplicar cor da nota
    function applyRatingColor(element, nota) {
        element.className = 'rating-badge'; // Reseta
        if (nota == 10) { element.classList.add('rating-perfect'); }
        else if (nota > 9) { element.classList.add('rating-great'); }
        else if (nota >= 8) { element.classList.add('rating-good'); }
        else if (nota >= 5) { element.classList.add('rating-medium'); }
        else { element.classList.add('rating-low'); }
    }

    // Abrir o modal de DETALHES
    athleteCards.forEach(card => {
        card.addEventListener('click', () => {
            const data = card.dataset; // Pega todos os data- attributes

            modalPhoto.src = data.photo;
            modalName.textContent = data.name;
            modalPosition.textContent = data.position;
            // Aba 1
            modalFullname.textContent = data.fullname;
            modalBirthdate.textContent = data.birthdate;
            modalAge.textContent = data.age + ' anos';
            modalNationality.textContent = data.nationality;
            modalHeight.textContent = data.height;
            modalWeight.textContent = data.weight;
            modalFoot.textContent = data.foot;
            // Aba 2 - Temporada
            modalGames.textContent = data.games;
            modalMinutes.textContent = data.minutes;
            modalGoals.textContent = data.goals;
            modalAssists.textContent = data.assists;
            modalYellow.textContent = data.yellow;
            modalRed.textContent = data.red;

            // Aba 2 - Último Treino (SofaScore)
            const notaGeral = parseFloat(data.treinoNotaGeral);
            modalTreinoNotaGeral.textContent = notaGeral.toFixed(1);
            applyRatingColor(modalTreinoNotaGeral, notaGeral);

            modalTreinoNotaTatica.textContent = parseFloat(data.treinoNotaTatica).toFixed(1);
            modalTreinoNotaTecnica.textContent = parseFloat(data.treinoNotaTecnica).toFixed(1);
            modalTreinoNotaFisica.textContent = parseFloat(data.treinoNotaFisica).toFixed(1);

            const passesPct = (parseInt(data.treinoPassesCertos) / parseInt(data.treinoPassesTotal) * 100);
            modalTreinoPasses.textContent = `${data.treinoPassesCertos} / ${data.treinoPassesTotal} (${passesPct.toFixed(0)}%)`;
            modalTreinoPassesBar.style.width = passesPct + '%';

            const chutesPct = (parseInt(data.treinoChutesCertos) / parseInt(data.treinoChutesTotal) * 100);
            modalTreinoChutes.textContent = `${data.treinoChutesCertos} / ${data.treinoChutesTotal} (${chutesPct.toFixed(0)}%)`;
            modalTreinoChutesBar.style.width = chutesPct + '%';

            const driblesPct = (parseInt(data.treinoDriblesCertos) / parseInt(data.treinoDriblesTotal) * 100);
            modalTreinoDribles.textContent = `${data.treinoDriblesCertos} / ${data.treinoDriblesTotal} (${driblesPct.toFixed(0)}%)`;
            modalTreinoDriblesBar.style.width = driblesPct + '%';

            modalTreinoDesarmes.textContent = data.treinoDesarmes;
            modalTreinoInterceptacoes.textContent = data.treinoInterceptacoes;
            modalTreinoKm.textContent = data.treinoKmPercorridos + ' km';
            modalTreinoVelMax.textContent = data.treinoVelocidadeMax + ' km/h';
            modalTreinoSprints.textContent = data.treinoSprints;

            // Aba 3
            modalMeta1.textContent = data.meta1;
            modalMeta2.textContent = data.meta2;
            modalObservation.textContent = data.observation;
            // Aba 4
            modalSalary.textContent = data.salary;
            modalContractStart.textContent = data.contractStart;
            modalContractEnd.textContent = data.contractEnd;
            modalReleaseClause.textContent = data.releaseClause;

            modal.classList.add('visible');
        });
    });

    // Fechar o modal de DETALHES
    const closeModal = () => { modal.classList.remove('visible'); };
    closeModalBtn.addEventListener('click', closeModal);
    modal.addEventListener('click', (event) => {
        if (event.target === modal) { closeModal(); }
    });

    // Lógica das Abas do modal de DETALHES
    const tabLinks = document.querySelectorAll('#athlete-modal .tab-link');
    const tabContents = document.querySelectorAll('#athlete-modal .tab-content');
    tabLinks.forEach(link => {
        link.addEventListener('click', () => {
            tabLinks.forEach(l => l.classList.remove('active'));
            tabContents.forEach(c => c.classList.remove('active'));
            const tabId = link.dataset.tab;
            const contentToShow = document.getElementById(tabId);
            link.classList.add('active');
            contentToShow.classList.add('active');
        });
    });

    // --- LÓGICA DO MODAL DE ADICIONAR ATLETA ---
    const addAthleteBtn = document.getElementById('add-athlete-btn');
    const addModal = document.getElementById('add-athlete-modal');
    const closeAddModalBtn = document.getElementById('close-add-modal-btn');

    // Abrir o modal de adicionar
    if (addAthleteBtn) {
        addAthleteBtn.addEventListener('click', () => {
            addModal.classList.add('visible');
        });
    }

    // Fechar o modal de adicionar
    const closeAddModal = () => {
        addModal.classList.remove('visible');
    }
    closeAddModalBtn.addEventListener('click', closeAddModal);
    addModal.addEventListener('click', (event) => {
        if (event.target === addModal) {
            closeAddModal();
        }
    });

    // Lógica das Abas do modal de ADICIONAR
    const addModalTabs = document.querySelectorAll('#add-athlete-modal .tab-link');
    const addModalTabContents = document.querySelectorAll('#add-athlete-modal .tab-content');

    addModalTabs.forEach(link => {
        link.addEventListener('click', (event) => {
            event.preventDefault(); // Impede que o botão de aba submeta o formulário
            addModalTabs.forEach(l => l.classList.remove('active'));
            addModalTabContents.forEach(c => c.classList.remove('active'));

            const tabId = link.dataset.tab;
            const contentToShow = document.getElementById(tabId);

            link.classList.add('active');
            contentToShow.classList.add('active');
        });
    });
});