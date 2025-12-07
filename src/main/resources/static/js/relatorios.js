document.addEventListener("DOMContentLoaded", function() {
    // Configurar eventos nos botões ao carregar a página
    const btnFluxo = document.getElementById("btn-fluxo-caixa");
    if (btnFluxo) {
        btnFluxo.addEventListener("click", gerarRelatorioFluxoCaixa);
    }

    const btnCategoria = document.getElementById("btn-categoria");
    if (btnCategoria) {
        btnCategoria.addEventListener("click", gerarRelatorioCategoria);
    }

    const btnPosJogo = document.getElementById("btn-pos-jogo");
    if (btnPosJogo) {
        btnPosJogo.addEventListener("click", gerarRelatorioPosJogo);
    }

    // Carregar eventos dinamicamente
    carregarEventos();
});

function carregarEventos() {
    fetch('/api/relatorios/eventos')
        .then(response => response.json())
        .then(eventos => {
            const select = document.getElementById('game-select');
            select.innerHTML = ''; // Limpa as opções existentes

            if (eventos.length === 0) {
                const option = document.createElement('option');
                option.value = '';
                option.textContent = 'Nenhum evento cadastrado';
                select.appendChild(option);
                return;
            }

            eventos.forEach(evento => {
                const option = document.createElement('option');
                option.value = evento.id;

                // Formata a data
                const data = new Date(evento.dataEvento);
                const dataFormatada = data.toLocaleDateString('pt-BR');

                // Monta o texto da opção
                const adversario = evento.adversario || 'Adversário não informado';
                option.textContent = `${dataFormatada} - ${evento.titulo} vs. ${adversario}`;

                select.appendChild(option);
            });
        })
        .catch(error => {
            console.error('Erro ao carregar eventos:', error);
            alert('Erro ao carregar a lista de eventos');
        });
}

/**
 * Função auxiliar para realizar o download do arquivo
 * O backend retorna um Content-Disposition: attachment, então window.location.href
 * inicia o download sem sair da página.
 */
function baixarArquivo(url) {
    // Verifica se os parâmetros estão preenchidos
    if (url.includes("=undefined") || url.includes("=null")) {
        alert("Erro: parâmetros inválidos na URL");
        return;
    }

    console.log("Baixando relatório de: " + url);
    window.location.href = url;
}

function gerarRelatorioFluxoCaixa() {
    const mes = document.getElementById("select-mes").value;
    const ano = document.getElementById("select-ano").value;

    if (!mes || !ano) {
        alert("Por favor, selecione o Mês e o Ano.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/fluxo-caixa?mes=${mes}&ano=${ano}`;
    baixarArquivo(url);
}

function gerarRelatorioCategoria() {
    const categoria = document.getElementById("select-categoria").value;

    if (!categoria) {
        alert("Por favor, selecione uma Categoria.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/categoria?nome=${encodeURIComponent(categoria)}`;
    baixarArquivo(url);
}

function gerarRelatorioPosJogo() {
    const eventoId = document.getElementById("game-select").value;

    if (!eventoId) {
        alert("Por favor, selecione um Jogo.");
        return;
    }

    // Monta a URL conforme definido no RelatoriosController
    const url = `/api/relatorios/pos-jogo?eventoId=${eventoId}`;
    baixarArquivo(url);
}