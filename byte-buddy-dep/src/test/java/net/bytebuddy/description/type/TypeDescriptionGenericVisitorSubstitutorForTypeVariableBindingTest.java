package net.bytebuddy.description.type;

import net.bytebuddy.test.utility.MockitoRule;
import net.bytebuddy.test.utility.ObjectPropertyAssertion;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TestRule;
import org.mockito.Mock;

import java.util.HashMap;
import java.util.Map;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class TypeDescriptionGenericVisitorSubstitutorForTypeVariableBindingTest {

    @Rule
    public TestRule mockitoRule = new MockitoRule(this);

    @Mock
    private TypeDescription.Generic source, target, unknown;

    private Map<TypeDescription.Generic, TypeDescription.Generic> mapping;

    @Before
    public void setUp() throws Exception {
        mapping = new HashMap<TypeDescription.Generic, TypeDescription.Generic>();
        mapping.put(source, target);
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testSimpleType() throws Exception {
        new TypeDescription.Generic.Visitor.Substitutor.ForTypeVariableBinding(mapping).onSimpleType(source);
    }

    @Test
    public void testNonGenericType() throws Exception {
        assertThat(new TypeDescription.Generic.Visitor.Substitutor.ForTypeVariableBinding(mapping).onNonGenericType(source), is(source));
    }

    @Test
    public void testTypeVariableKnown() throws Exception {
        assertThat(new TypeDescription.Generic.Visitor.Substitutor.ForTypeVariableBinding(mapping).onTypeVariable(source), is(target));
    }

    @Test(expected = IllegalStateException.class)
    public void testTypeVariableUnknown() throws Exception {
        new TypeDescription.Generic.Visitor.Substitutor.ForTypeVariableBinding(mapping).onTypeVariable(unknown);
    }

    @Test
    public void testObjectProperties() throws Exception {
        ObjectPropertyAssertion.of(TypeDescription.Generic.Visitor.Substitutor.ForTypeVariableBinding.class).apply();
    }
}