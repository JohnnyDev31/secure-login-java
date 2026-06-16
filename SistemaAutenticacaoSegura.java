import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class SistemaAutenticacaoSegura {

    // Simulação de um Banco de Dados em Memória
    private static final Map<String, String> BANCO_DE_DADOS_USUARIOS = new HashMap<>();
    
    // Controle de Força Bruta: Mapeia o usuário ao número de tentativas erradas
    private static final Map<String, Integer> TENTATIVAS_LOGIN = new HashMap<>();
    private static final int MAX_TENTATIVAS = 3;

    public static void main(String[] args) {
        // Carga inicial de dados simulados (Usuário e Senha)
        // Em um cenário real, a senha estaria criptografada com um hash forte (ex: bcrypt)
        BANCO_DE_DADOS_USUARIOS.put("joao.silva@email.com", "SenhaSegura123");
        BANCO_DE_DADOS_USUARIOS.put("admin@empresa.com.br", "AdminMaster2026");

        Scanner scanner = new Scanner(System.in);
        System.out.println("=== SISTEMA DE AUTENTICAÇÃO SEGURO INICIADO ===");

        // Loop de simulação do prompt de login
        while (true) {
            System.out.print("\nDigite o e-mail de usuário (ou 'sair'): ");
            String usuario = scanner.nextLine();

            if (usuario.equalsIgnoreCase("sair")) {
                System.out.println("Encerrando o sistema de segurança.");
                break;
            }

            System.out.print("Digite a senha: ");
            String senha = scanner.nextLine();

            // Executa a tentativa de login passando pelos filtros de AppSec
            realizarLoginSeguro(usuario, senha);
        }
        scanner.close();
    }

    /**
     * Método principal de autenticação aplicando conceitos de OWASP Top 10
     */
    public static void realizarLoginSeguro(String usuarioInput, String senhaInput) {
        
        // 1. DEFESA CONTRA FORÇA BRUTA (Rate Limiting / Account Lockout)
        if (isContaBloqueada(usuarioInput)) {
            System.out.println("[⚠️ ALERTA DE SEGURANÇA] Conta temporariamente bloqueada devido a múltiplos erros de login.");
            return;
        }

        // 2. DEFESA CONTRA SQL INJECTION (Simulação de Prepared Statement)
        // Em Java/JDBC real, o código seria: 
        // PreparedStatement stmt = conn.prepareStatement("SELECT password FROM users WHERE email = ?");
        // stmt.setString(1, usuarioInput);
        // O uso do "?" garante que o input do usuário seja tratado estritamente como DADO (texto), nunca como COMANDO SQL.
        
        System.out.println("[*] Processando consulta parametrizada (Prepared Statement) de forma segura...");
        String senhaArmazenada = BANCO_DE_DADOS_USUARIOS.get(usuarioInput);

        // 3. VALIDAÇÃO DE CREDENCIAIS
        if (senhaArmazenada != null && senhaArmazenada.equals(senhaInput)) {
            System.out.println("[🟢 SUCESSO] Autenticação realizada com sucesso! Bem-vindo.");
            // Reseta o contador de tentativas em caso de sucesso
            TENTATIVAS_LOGIN.put(usuarioInput, 0);
        } else {
            System.out.println("[🔴 ERRO] Credenciais inválidas.");
            // Registra a falha para o controle de força bruta
            registrarFalhaLogin(usuarioInput);
        }
    }

    private static boolean isContaBloqueada(String usuario) {
        Integer tentativas = TENTATIVAS_LOGIN.get(usuario);
        return tentativas != null && tentativas >= MAX_TENTATIVAS;
    }

    private static void registrarFalhaLogin(String usuario) {
        int tentativasAtuais = TENTATIVAS_LOGIN.getOrDefault(usuario, 0) + 1;
        TENTATIVAS_LOGIN.put(usuario, tentativasAtuais);
        
        System.out.println("[*] Tentativas incorretas para o usuário '" + usuario + "': " + tentativasAtuais + "/" + MAX_TENTATIVAS);
        
        if (tentativasAtuais >= MAX_TENTATIVAS) {
            System.out.println("[🚨 CRÍTICO] Limite de tentativas atingido. O usuário '" + usuario + "' foi bloqueado.");
        }
    }
}