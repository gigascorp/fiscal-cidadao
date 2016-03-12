using Microsoft.Owin;
using Owin;

[assembly: OwinStartupAttribute(typeof(FiscalCidadaoWeb.Startup))]
namespace FiscalCidadaoWeb
{
    public partial class Startup
    {
        public void Configuration(IAppBuilder app)
        {
            ConfigureAuth(app);
        }
    }
}
