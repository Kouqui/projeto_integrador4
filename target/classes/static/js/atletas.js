document.addEventListener('DOMContentLoaded', function () {
    
    // --- LÓGICA DO MODAL DE DETALHES (VISUALIZAÇÃO) ---
    const modal = document.getElementById('athlete-modal');
    if (modal) {
        const athleteCards = document.querySelectorAll('.athlete-card');
        const closeModalBtn = document.getElementById('close-modal-btn');

        // Elementos do Modal de Detalhes
        const modalPhoto = document.getElementById('modal-photo');
        const modalName = document.getElementById('modal-name');
        const modalPosition = document.getElementById('modal-position');
        const modalFullname = document.getElementById('modal-fullname');
        const modalBirthdate = document.getElementById('modal-birthdate');
        const modalAge = document.getElementById('modal-age');
        const modalNationality = document.getElementById('modal-nationality');
        const modalHeight = document.getElementById('modal-height');
        const modalWeight = document.getElementById('modal-weight');
        const modalFoot = document.getElementById('modal-foot');
        const modalGames = document.getElementById('modal-games');
        const modalMinutes = document.getElementById('modal-minutes');
        const modalGoals = document.getElementById('modal-goals');
        const modalAssists = document.getElementById('modal-assists');
        const modalYellow = document.getElementById('modal-yellow');
        const modalRed = document.getElementById('modal-red');

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

        const modalMeta1 = document.getElementById('modal-meta-1');
        const modalMeta2 = document.getElementById('modal-meta-2');
        const modalObservation = document.getElementById('modal-observation');
        const modalSalary = document.getElementById('modal-salary');
        const modalContractStart = document.getElementById('modal-contract-start');
        const modalContractEnd = document.getElementById('modal-contract-end');
        const modalReleaseClause = document.getElementById('modal-release-clause');

        // Botões para abrir os novos modais
        const showAddTreinoBtn = document.getElementById('show-add-treino-modal-btn');
        const addTreinoModal = document.getElementById('add-treino-modal');
        const hiddenAtletaIdInput = document.getElementById('treino-atleta-id');

        // Função para aplicar cor da nota
        function applyRatingColor(element, nota) {
            element.className = 'rating-badge'; // Reseta
            if (nota == 10) { element.classList.add('rating-perfect'); }
            else if (nota > 9) { element.classList.add('rating-great'); }
            else if (nota >= 8) { element.classList.add('rating-good'); }
            else if (nota >= 5) { element.classList.add('rating-medium'); }
            else { element.classList.add('rating-low'); }
        }

        // Função segura para calcular porcentagem
        function calculatePercentage(certos, total) {
            const numCertos = parseInt(certos) || 0;
            const numTotal = parseInt(total) || 1;
            if (numTotal === 0 || numCertos === 0) return 0;
            return (numCertos / numTotal * 100);
        }

        // Abrir o modal de DETALHES
        athleteCards.forEach(card => {
            card.addEventListener('click', () => {
                const data = card.dataset;

                // --- Preenche o modal de Detalhes ---
                modalPhoto.src = data.photo || '';
                modalName.textContent = data.name || 'N/A';
                modalPosition.textContent = data.position || 'N/A';
                modalFullname.textContent = data.fullname || 'N/A';
                modalBirthdate.textContent = data.birthdate || 'N/A';
                modalAge.textContent = (data.age || 0) + ' anos';
                modalNationality.textContent = data.nationality || 'N/A';
                modalHeight.textContent = data.height || 'N/A';
                modalWeight.textContent = data.weight || 'N/A';
                modalFoot.textContent = data.foot || 'N/A';
                modalGames.textContent = data.games || 0;
                modalMinutes.textContent = data.minutes || 0;
                modalGoals.textContent = data.goals || 0;
                modalAssists.textContent = data.assists || 0;
                modalYellow.textContent = data.yellow || 0;
                modalRed.textContent = data.red || 0;

                const notaGeral = parseFloat(data.treinoNotaGeral) || 0;
                modalTreinoNotaGeral.textContent = notaGeral.toFixed(1);
                applyRatingColor(modalTreinoNotaGeral, notaGeral);

                modalTreinoNotaTatica.textContent = (parseFloat(data.treinoNotaTatica) || 0).toFixed(1);
                modalTreinoNotaTecnica.textContent = (parseFloat(data.treinoNotaTecnica) || 0).toFixed(1);
                modalTreinoNotaFisica.textContent = (parseFloat(data.treinoNotaFisica) || 0).toFixed(1);
                const passesCertos = data.treinoPassesCertos || 0;
                const passesTotal = data.treinoPassesTotal || 1;
                const passesPct = calculatePercentage(passesCertos, passesTotal);
                modalTreinoPasses.textContent = `${passesCertos} / ${passesTotal} (${passesPct.toFixed(0)}%)`;
                modalTreinoPassesBar.style.width = passesPct + '%';
                const chutesCertos = data.treinoChutesCertos || 0;
                const chutesTotal = data.treinoChutesTotal || 1;
                const chutesPct = calculatePercentage(chutesCertos, chutesTotal);
                modalTreinoChutes.textContent = `${chutesCertos} / ${chutesTotal} (${chutesPct.toFixed(0)}%)`;
                modalTreinoChutesBar.style.width = chutesPct + '%';
                const driblesCertos = data.treinoDriblesCertos || 0;
                const driblesTotal = data.treinoDriblesTotal || 1;
                const driblesPct = calculatePercentage(driblesCertos, driblesTotal);
                modalTreinoDribles.textContent = `${driblesCertos} / ${driblesTotal} (${driblesPct.toFixed(0)}%)`;
                modalTreinoDriblesBar.style.width = driblesPct + '%';
                modalTreinoDesarmes.textContent = data.treinoDesarmes || 0;
                modalTreinoInterceptacoes.textContent = data.treinoInterceptacoes || 0;
                modalTreinoKm.textContent = (data.treinoKmPercorridos || 0) + ' km';
                modalTreinoVelMax.textContent = (data.treinoVelocidadeMax || 0) + ' km/h';
                modalTreinoSprints.textContent = data.treinoSprints || 0;
                modalMeta1.textContent = data.meta1 || 'N/A';
                modalMeta2.textContent = data.meta2 || '';
                modalObservation.textContent = data.observation || 'Sem observações.';
                modalSalary.textContent = data.salary || 'N/A';
                modalContractStart.textContent = data.contractStart || 'N/A';
                modalContractEnd.textContent = data.contractEnd || 'N/A';
                modalReleaseClause.textContent = data.releaseClause || 'N/A';

                // --- Prepara o modal de Adicionar Treino ---
                // Pega o ID do atleta e coloca no campo escondido do formulário de treino
                if(hiddenAtletaIdInput) {
                    hiddenAtletaIdInput.value = data.id;
                }

                modal.classList.add('visible');
            });
        });

        // Fechar o modal de DETALHES
        const closeModal = () => { modal.classList.remove('visible'); };
        if(closeModalBtn) closeModalBtn.addEventListener('click', closeModal);
        modal.addEventListener('click', (event) => {
            if (event.target === modal) { closeModal(); }
        });

        // Lógica das Abas do modal de DETALHES
        const tabLinks = document.querySelectorAll('#athlete-modal .tab-link');
        const tabContents = document.querySelectorAll('#athlete-modal .tab-content');
        tabLinks.forEach(link => {
            link.addEventListener('click', (event) => {
                event.preventDefault();
                tabLinks.forEach(l => l.classList.remove('active'));
                tabContents.forEach(c => c.classList.remove('active'));
                const tabId = link.dataset.tab;
                const contentToShow = document.getElementById(tabId);
                if(contentToShow) {
                    link.classList.add('active');
                    contentToShow.classList.add('active');
                }
            });
        });

        // Lógica para abrir o NOVO modal de TREINO (a partir do modal de Detalhes)
        if (showAddTreinoBtn && addTreinoModal) {
            showAddTreinoBtn.addEventListener('click', () => {
                modal.classList.remove('visible'); // Fecha o modal de detalhes
                addTreinoModal.classList.add('visible'); // Abre o modal de treino
            });
        }

    } // Fim do 'if (modal)'


    // --- LÓGICA DO MODAL DE ADICIONAR ATLETA ---
    const addModal = document.getElementById('add-athlete-modal');
    if (addModal) {
        const addAthleteBtn = document.getElementById('add-athlete-btn');
        const closeAddModalBtn = document.getElementById('close-add-modal-btn');

        if (addAthleteBtn) {
            addAthleteBtn.addEventListener('click', () => {
                addModal.classList.add('visible');
            });
        }

        const closeAddModal = () => {
            addModal.classList.remove('visible');
        }
        if(closeAddModalBtn) closeAddModalBtn.addEventListener('click', closeAddModal);
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
                event.preventDefault();
                addModalTabs.forEach(l => l.classList.remove('active'));
                addModalTabContents.forEach(c => c.classList.remove('active'));

                const tabId = link.dataset.tab;
                const contentToShow = document.getElementById(tabId);
                if(contentToShow) {
                    link.classList.add('active');
                    contentToShow.classList.add('active');
                }
            });
        });
    } // Fim do 'if (addModal)'

    // --- LÓGICA DO NOVO MODAL DE ADICIONAR TREINO ---
    const addTreinoModal = document.getElementById('add-treino-modal');
    if (addTreinoModal) {
        const closeAddTreinoModalBtn = document.getElementById('close-add-treino-modal-btn');
        const closeAddTreinoModal = () => {
            addTreinoModal.classList.remove('visible');
        }
        if(closeAddTreinoModalBtn) closeAddTreinoModalBtn.addEventListener('click', closeAddTreinoModal);
        addTreinoModal.addEventListener('click', (event) => {
            if (event.target === addTreinoModal) {
                closeAddModal();
            }
        });
    }
});