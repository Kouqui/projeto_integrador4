document.addEventListener('DOMContentLoaded', function () {
    // Criado pelo autor: Kauã Kouqui
    // ==================================================================
    // 0. LÓGICA DE FILTROS (BUSCA EM TEMPO REAL) - RESTAURADA
    // ==================================================================
    const searchInput = document.getElementById('search-input');
    const categoryFilter = document.getElementById('category-filter');
    const positionFilter = document.getElementById('position-filter');
    const athleteCards = document.querySelectorAll('.athlete-card');

    function filterAthletes() {
        const term = searchInput.value.toLowerCase();
        const category = categoryFilter.value;
        const position = positionFilter.value;

        athleteCards.forEach(card => {
            // Pega os dados escondidos no card (garante que não seja null)
            const name = (card.dataset.name || '').toLowerCase();
            const cat = card.dataset.category || '';
            const pos = card.dataset.position || '';

            // Verifica se corresponde aos filtros
            const matchesTerm = name.includes(term);
            const matchesCat = (category === 'all' || cat === category);
            const matchesPos = (position === 'all' || pos === position);

            // Mostra ou esconde o card
            if (matchesTerm && matchesCat && matchesPos) {
                card.style.display = 'block';
            } else {
                card.style.display = 'none';
            }
        });
    }

    // Ativa os filtros quando o usuário digita ou muda a opção
    if (searchInput) searchInput.addEventListener('input', filterAthletes);
    if (categoryFilter) categoryFilter.addEventListener('change', filterAthletes);
    if (positionFilter) positionFilter.addEventListener('change', filterAthletes);


    // ==================================================================
    // 1. FUNÇÕES AUXILIARES (PARA O MODAL DE DETALHES)
    // ==================================================================
    function setText(id, text) { const el = document.getElementById(id); if(el) el.textContent = text || (text === 0 ? '0' : ''); }
    function setSrc(id, src) { const el = document.getElementById(id); if(el) el.src = src || ''; }
    function setHtml(id, html) { const el = document.getElementById(id); if(el) el.innerHTML = html || ''; }
    function setStyleWidth(id, percent) { const el = document.getElementById(id); if(el) el.style.width = percent + '%'; }

    function applyRatingColor(id, nota) {
        const element = document.getElementById(id);
        if (!element) return;
        element.className = 'rating-badge';
        if (nota == 10) element.classList.add('rating-perfect');
        else if (nota > 9) element.classList.add('rating-great');
        else if (nota >= 8) element.classList.add('rating-good');
        else if (nota >= 5) element.classList.add('rating-medium');
        else element.classList.add('rating-low');
        element.textContent = nota.toFixed(1);
    }

    function calculatePercentage(certos, total) {
        const c = parseInt(certos) || 0;
        const t = parseInt(total) || 1;
        if (t === 0) return 0;
        return (c / t * 100);
    }

    // Variável para guardar os dados do atleta clicado (para edição)
    let currentAthleteData = {};


    // ==================================================================
    // 2. LÓGICA DO MODAL DE DETALHES (CLIQUE NO CARD)
    // ==================================================================
    const modalDetails = document.getElementById('athlete-modal');

    if (modalDetails) {
        const closeBtn = document.getElementById('close-modal-btn');

        athleteCards.forEach(card => {
            card.addEventListener('click', () => {
                const d = card.dataset;
                currentAthleteData = d; // Salva para usar nos formulários de edição

                // --- Preenchimento dos Campos ---
                setSrc('modal-photo', d.photo);
                setText('modal-name', d.name);
                setText('modal-position', d.position);

                // Aba Ficha
                setText('modal-fullname', d.fullname);
                setText('modal-birthdate', d.birthdate);
                setText('modal-age', (d.age || '0') + ' anos');
                setText('modal-nationality', d.nationality);
                setText('modal-height', d.height);
                setText('modal-weight', d.weight);
                setText('modal-foot', d.foot);

                // Aba Desempenho (SofaScore)
                const nota = parseFloat(d.treinoNotaGeral) || 0;
                applyRatingColor('modal-treino-nota-geral', nota);

                setText('modal-treino-nota-tatica', (parseFloat(d.treinoNotaTatica)||0).toFixed(1));
                setText('modal-treino-nota-tecnica', (parseFloat(d.treinoNotaTecnica)||0).toFixed(1));
                setText('modal-treino-nota-fisica', (parseFloat(d.treinoNotaFisica)||0).toFixed(1));

                const pPct = calculatePercentage(d.treinoPassesCertos, d.treinoPassesTotal);
                setHtml('modal-treino-passes', `${d.treinoPassesCertos||0} / ${d.treinoPassesTotal||0}`);
                setStyleWidth('modal-treino-passes-bar', pPct);

                const cPct = calculatePercentage(d.treinoChutesCertos, d.treinoChutesTotal);
                setHtml('modal-treino-chutes', `${d.treinoChutesCertos||0} / ${d.treinoChutesTotal||0}`);
                setStyleWidth('modal-treino-chutes-bar', cPct);

                const drPct = calculatePercentage(d.treinoDriblesCertos, d.treinoDriblesTotal);
                setHtml('modal-treino-dribles', `${d.treinoDriblesCertos||0} / ${d.treinoDriblesTotal||0}`);
                setStyleWidth('modal-treino-dribles-bar', drPct);

                setText('modal-treino-desarmes', d.treinoDesarmes);
                setText('modal-treino-interceptacoes', d.treinoInterceptacoes);
                setText('modal-treino-km', (d.treinoKmPercorridos||0) + ' km');
                setText('modal-treino-vel-max', (d.treinoVelocidadeMax||0) + ' km/h');
                setText('modal-treino-sprints', d.treinoSprints);

                // Aba Desempenho (Temporada)
                setText('modal-games', d.games);
                setText('modal-minutes', d.minutes);
                setText('modal-goals', d.goals);
                setText('modal-assists', d.assists);
                setText('modal-yellow', d.yellow);
                setText('modal-red', d.red);

                // Aba Carreira
                setText('modal-meta-1', d.meta1);
                setText('modal-meta-2', d.meta2);
                setText('modal-observation', d.observation);

                // Aba Financeiro
                setText('modal-salary', d.salary);
                setText('modal-release-clause', d.releaseClause);
                setText('modal-contract-start', d.contractStart);
                setText('modal-contract-end', d.contractEnd);

                // Passa IDs para os inputs escondidos dos formulários
                const hiddenTreinoId = document.getElementById('treino-atleta-id');
                if(hiddenTreinoId) hiddenTreinoId.value = d.id;
                const hiddenStatsId = document.getElementById('stats-atleta-id');
                if(hiddenStatsId) hiddenStatsId.value = d.id;

                modalDetails.classList.add('visible');
            });
        });

        if(closeBtn) closeBtn.addEventListener('click', () => modalDetails.classList.remove('visible'));
        modalDetails.addEventListener('click', (e) => { if (e.target === modalDetails) modalDetails.classList.remove('visible'); });

        // Lógica das Abas Internas
        const tabs = document.querySelectorAll('#athlete-modal .tab-link');
        const contents = document.querySelectorAll('#athlete-modal .tab-content');
        tabs.forEach(tab => {
            tab.addEventListener('click', (e) => {
                e.preventDefault();
                tabs.forEach(t => t.classList.remove('active'));
                contents.forEach(c => c.classList.remove('active'));
                const target = document.getElementById(tab.dataset.tab);
                if(target) {
                    tab.classList.add('active');
                    target.classList.add('active');
                }
            });
        });
    }


    // ==================================================================
    // 3. LÓGICA GENÉRICA PARA OS MODAIS DE FORMULÁRIO
    // ==================================================================
    function setupFormModal(modalId, openBtnId, closeBtnId, prefillCallback) {
        const modal = document.getElementById(modalId);
        const openBtn = document.getElementById(openBtnId);
        const closeBtn = document.getElementById(closeBtnId);

        if (modal && openBtn) {
            openBtn.addEventListener('click', () => {
                if (prefillCallback) prefillCallback(); // Preenche dados se necessário
                if (modalDetails && modalDetails.classList.contains('visible')) {
                    modalDetails.classList.remove('visible'); // Fecha o perfil se estiver aberto
                }
                modal.classList.add('visible');
            });

            if(closeBtn) closeBtn.addEventListener('click', () => modal.classList.remove('visible'));
            modal.addEventListener('click', (e) => { if (e.target === modal) modal.classList.remove('visible'); });

            // Abas internas dos formulários
            const tabs = modal.querySelectorAll('.tab-link');
            const contents = modal.querySelectorAll('.tab-content');
            tabs.forEach(tab => {
                tab.addEventListener('click', (e) => {
                    e.preventDefault();
                    tabs.forEach(t => t.classList.remove('active'));
                    contents.forEach(c => c.classList.remove('active'));
                    const target = modal.querySelector('#' + tab.dataset.tab);
                    if(target) {
                        tab.classList.add('active');
                        target.classList.add('active');
                    }
                });
            });
        }
    }

    // Configura Modal: ADICIONAR ATLETA
    setupFormModal('add-athlete-modal', 'add-athlete-btn', 'close-add-modal-btn');

    // Configura Modal: NOVO TREINO (Preenche com dados atuais para edição)
    setupFormModal('add-treino-modal', 'show-add-treino-modal-btn', 'close-add-treino-modal-btn', () => {
        const d = currentAthleteData;
        // IDs
        document.getElementById('treino-atleta-id').value = d.id;
        document.getElementById('form-treino-id').value = d.treinoId || '';

        // Notas
        document.getElementById('input-treino-tatica').value = d.treinoNotaTatica || '';
        document.getElementById('input-treino-fisica').value = d.treinoNotaFisica || '';

        // Técnicos
        document.getElementById('input-treino-passes-c').value = d.treinoPassesCertos || '';
        document.getElementById('input-treino-passes-t').value = d.treinoPassesTotal || '';
        document.getElementById('input-treino-chutes-c').value = d.treinoChutesCertos || '';
        document.getElementById('input-treino-chutes-t').value = d.treinoChutesTotal || '';
        document.getElementById('input-treino-dribles-c').value = d.treinoDriblesCertos || '';
        document.getElementById('input-treino-dribles-t').value = d.treinoDriblesTotal || '';
        document.getElementById('input-treino-desarmes').value = d.treinoDesarmes || '';
        document.getElementById('input-treino-interceptacoes').value = d.treinoInterceptacoes || '';

        // Físicos
        document.getElementById('input-treino-km').value = d.treinoKmPercorridos || '';
        document.getElementById('input-treino-vel').value = d.treinoVelocidadeMax || '';
        document.getElementById('input-treino-sprints').value = d.treinoSprints || '';
    });

    // Configura Atualizar Stats (com preenchimento)
        setupFormModal('add-stats-modal', 'show-add-stats-modal-btn', 'close-add-stats-modal-btn', () => {
            const d = currentAthleteData;
            document.getElementById('stats-atleta-id').value = d.id;
            document.getElementById('stats-id').value = d.statsId || '';

            document.getElementById('input-stats-temp').value = '2025';
            document.getElementById('input-stats-jogos').value = d.games || '';
            document.getElementById('input-stats-gols').value = d.goals || '';
            document.getElementById('input-stats-assists').value = d.assists || '';
            document.getElementById('input-stats-min').value = d.minutes || '';
            // CERTIFIQUE-SE QUE ESTAS LINHAS ESTÃO AQUI:
            document.getElementById('input-stats-y').value = d.yellow || '';
            document.getElementById('input-stats-r').value = d.red || '';
        });

});