package factories;

import entidades.Aliado;
import entidades.Habilidade;
import entidades.TipoHabilidade;
import org.junit.jupiter.api.Test;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.junit.jupiter.api.Assertions.*;

class HabilidadeFactoryTest {

    @Test
    void deveCriarTodasAsHabilidades() throws Exception {
        Aliado dummy = PersonagemFactory.criarLuffy();
        Method[] methods = HabilidadeFactory.class.getDeclaredMethods();
        
        for (Method m : methods) {
            if (Modifier.isStatic(m.getModifiers()) && m.getReturnType().equals(Habilidade.class)) {
                Habilidade h = null;
                if (m.getParameterCount() == 0) {
                    h = (Habilidade) m.invoke(null);
                } else if (m.getParameterCount() == 1 && m.getParameterTypes()[0].equals(Aliado.class)) {
                    h = (Habilidade) m.invoke(null, dummy);
                } else if (m.getParameterCount() == 1 && m.getParameterTypes()[0].equals(int.class)) {
                    h = (Habilidade) m.invoke(null, 1);
                }
                
                if (h != null) {
                    assertNotNull(h.getNome(), "Metodo " + m.getName() + " deve ter nome");
                    assertNotNull(h.getTipo(), "Metodo " + m.getName() + " deve ter tipo");
                }
            }
        }
    }
}
