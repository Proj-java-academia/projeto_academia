/*
1 login
2 coneção com o banco de dados academiajava
3 criar usuarios
4 deletar usuarios.
*/

    //email = //email do loginacademia.html
    //senha = //senha do loginacademia.html
    //se apertar o botão entrar ele verifica as informações enviadas e então se verdadeiro proxima pag se falso aparece tente novamente
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

        // Dados vindos do formulário
        String email = request.getParameter("email");
        String senha = request.getParameter("senha");

        // Verificação simples (sem banco)
        if ("admn".equals(email) && "1234".equals(senha)) {
            response.sendRedirect("pagina_inicial.html");
        } else {
            response.sendRedirect("loginacademia.html?erro=1");
        }
    }
}








/*tentativa de conexão com o banco de dados*/
port java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao {
    private static final String URL = "jdbc:mysql://localhost:3306/academiajava";
    private static final String USUARIO = "root";
    private static final String SENHA = "1234"

    public static Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }
}

