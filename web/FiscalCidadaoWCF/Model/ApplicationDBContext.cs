namespace FiscalCidadaoWCF.Models
{
    using System;
    using System.Data.Entity;
    using System.Linq;

    public class ApplicationDBContext : DbContext
    {
        public ApplicationDBContext()
            : base("name=ApplicationDBContext")
        {
        }

        public DbSet<Convenio> Convenio { get; set; }

        public DbSet<Denuncia> Denuncia { get; set; }

        public DbSet<DenunciaFoto> DenunciaFoto { get; set; }

        public DbSet<HistoricoAtualizacaoLocalizacao> HistoricoAtualizacaoLocalizacao { get; set; }

        public DbSet<ParecerGoverno> ParecerGoverno { get; set; }

        public DbSet<PedidoAtualizacaoLocalizacao> PedidoAtualizacaoLocalizacao { get; set; }

        public DbSet<Proponente> Proponente { get; set; }

        public DbSet<Situacao> Situacao { get; set; }

        public DbSet<Usuario> Usuario { get; set; }
    }

}