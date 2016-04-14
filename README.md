# Fiscal Cidadão
O aplicativo Fiscal Cidadão tem como proposta apresentar para o usuário, em sua interface, convênios ﬁrmados entre governo e proponentes em um mapa georeferenciado, de forma que o cidadão possa ﬁscalizar esses convênios e enviar dados sobre o andamento dos mesmos através do seu smartphone. Além disso, o sistema oferece para o governo a administração total desses dados através de uma plataforma web, permitindo a análise de informações relevantes, como um relatório com os proponentes com maior número de denúncias ou a região com maior índice de convênios irregulares, dentre outras possibilidades.

## Aplicação Web
http://www.fiscalcidadao.site

### Módulos e funcionalidades implementadas no MVP
* Home
    * Top 3 dos convênios mais denunciados.
    * Contador de denúncias não analisadas.
    * Contador de solicitações de atualizações de endereço de convênio.
* Convênios
    * Listagem de convênios
    * Filtragem de convênios por texto e data.
    * Fornecer um parecer (NÃO VERIFICADO, EM ANÁLISE, VERIFICADO - REGULAR, VERIFICADO - IRREGULAR) sobre o convênio para a população.
    * Detalhes do convênio
        * Ver o convênio no sincov
        * Dados do convênio
        * Lista de denúncias do convênio
            * Mensagem do usuário
            * Data da denúncia
            * Fotos da denúncia

## Aplicação Móvel - Android e IOS

### Módulos e funcionalidades implementadas no MVP (funcionando em ambas as plataformas)
* Convênios
    * Visualização de convênios (mapa) próximos ao usuário.
    * Ver detalhes do convênio (Descrição, Proponente, Responsável, Prazos, Valores)    
    * Denunciar convênio
        * Adição de mensagem de denúncia.
        * Adição de fotos à denúncia.
* Listagem de Denúncias
* Ranking de amigos fiscalizadores
* Perfil

## Como rodar o aplicativo
### Android
Para executar o aplicativo do Fiscal Cidadão você deve:
   * Habilitar a aplicativos de Fontes de Desconhecidas em seu celular através da opção Configurações > Segurança > Fontes Desconhecidas
   * Baixar, no seu celular, o apk do aplicativo que está neste repositório em "android/apk/fiscal-cidadao.apk" ou diretamente <a href="https://github.com/gigascorp/fiscal-cidadao/blob/master/android/apk/fiscal-cidadao.apk?raw=true">através deste link</a>
   * Utilizar no navegador de arquivos do seu celular para abrir o arquivo baixado, com isso a aplicação será instalada.

Se você quiser abrir o código fonte do aplicativo deve iniciar o AndroidStudio e utilizar a opção "Importar Projeto".

### IOS
   Para rodar este aplicativo no IOS, acesse nosso tutorial <a href="https://www.dropbox.com/s/ssx49f3o3g4dzyv/Procedimento%20para%20executar%20o%20aplicativo%20Fiscal%20Cidad%C3%A3o%20no%20Xcode.pdf?dl=0" target="_blank">neste link</a>
