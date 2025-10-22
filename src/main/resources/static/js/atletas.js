document.addEventListener('DOMContentLoaded', function () {
    const athleteCards = document.querySelectorAll('.athlete-card');
    const modal = document.getElementById('athlete-modal');
    const closeModalBtn = document.getElementById('close-modal-btn');

    // Elementos do Modal para popular com dados
    const modalPhoto = document.getElementById('modal-photo');
    const modalName = document.getElementById('modal-name');
    const modalPosition = document.getElementById('modal-position');
    const modalFullname = document.getElementById('modal-fullname');
    const modalBirthdate = document.getElementById('modal-birthdate');
    const modalAge = document.getElementById('modal-age');
    const modalNationality = document.getElementById('modal-nationality');
    const modalSalary = document.getElementById('modal-salary');
    const modalContractStart = document.getElementById('modal-contract-start');
    const modalContractEnd = document.getElementById('modal-contract-end');
    const modalReleaseClause = document.getElementById('modal-release-clause');

    // Abrir o modal e popular com os dados do card clicado
    athleteCards.forEach(card => {
        card.addEventListener('click', () => {
            // Pega os dados dos atributos data-* do card
            modalPhoto.src = card.dataset.photo;
            modalName.textContent = card.dataset.name;
            modalPosition.textContent = card.dataset.position;
            modalFullname.textContent = card.dataset.fullname;
            modalBirthdate.textContent = card.dataset.birthdate;
            modalAge.textContent = card.dataset.age + ' anos';
            modalNationality.textContent = card.dataset.nationality;
            modalSalary.textContent = card.dataset.salary;
            modalContractStart.textContent = card.dataset.contractStart;
            modalContractEnd.textContent = card.dataset.contractEnd;
            modalReleaseClause.textContent = card.dataset.releaseClause;

            modal.classList.add('visible');
        });
    });

    // Função para fechar o modal
    const closeModal = () => {
        modal.classList.remove('visible');
    };

    closeModalBtn.addEventListener('click', closeModal);
    modal.addEventListener('click', (event) => {
        if (event.target === modal) {
            closeModal();
        }
    });

    // Lógica das Abas (Tabs)
    const tabLinks = document.querySelectorAll('.tab-link');
    const tabContents = document.querySelectorAll('.tab-content');

    tabLinks.forEach(link => {
        link.addEventListener('click', () => {
            // Remove a classe 'active' de todos
            tabLinks.forEach(l => l.classList.remove('active'));
            tabContents.forEach(c => c.classList.remove('active'));

            // Adiciona a classe 'active' ao link clicado e ao conteúdo correspondente
            const tabId = link.dataset.tab;
            const contentToShow = document.getElementById(tabId);

            link.classList.add('active');
            contentToShow.classList.add('active');
        });
    });
});