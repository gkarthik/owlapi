/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2014, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.semanticweb.owlapi.api.test.searcher;

import static org.junit.Assert.assertTrue;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;
import static org.semanticweb.owlapi.search.Searcher.*;

import org.junit.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;

@SuppressWarnings("javadoc")
public class SearcherTestCase extends TestBase {
    @Test
    public void shouldSearch() throws OWLOntologyCreationException {
        // given
        OWLOntology o = m.createOntology();
        OWLClass c = Class(IRI("urn:c"));
        OWLClass d = Class(IRI("urn:d"));
        OWLAxiom ax = SubClassOf(c, d);
        o.getOWLOntologyManager().addAxiom(o, ax);
        assertTrue(find().axiomsOfType(AxiomType.SUBCLASS_OF).in(o)
                .contains(ax));
        assertTrue(find().classes().in(o).contains(c));
        assertTrue(find().classes().in(o).contains(d));
        assertTrue(describe(c).in(o).contains(ax));
    }

    @Test
    public void shouldSearchObjectProperties()
            throws OWLOntologyCreationException {
        // given
        OWLOntology o = m.createOntology();
        OWLObjectProperty c = ObjectProperty(IRI("urn:c"));
        OWLObjectProperty d = ObjectProperty(IRI("urn:d"));
        OWLObjectProperty e = ObjectProperty(IRI("urn:e"));
        OWLClass x = Class(IRI("urn:x"));
        OWLClass y = Class(IRI("urn:Y"));
        OWLAxiom ax = SubObjectPropertyOf(c, d);
        OWLAxiom ax2 = ObjectPropertyDomain(c, x);
        OWLAxiom ax3 = ObjectPropertyRange(c, y);
        OWLAxiom ax4 = EquivalentObjectProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(find().axiomsOfType(AxiomType.SUB_OBJECT_PROPERTY).in(o)
                .contains(ax));
        assertTrue(find().sub().propertiesOf(d).in(o).contains(c));
        assertTrue(find().sup().propertiesOf(c).in(o).contains(d));
        assertTrue(find().domains(c).in(o).contains(x));
        assertTrue(find().ranges(c).in(o).contains(y));
        assertTrue(find().equivalent().propertiesOf(c).in(o).contains(e));
    }

    @Test
    public void shouldSearchDataProperties()
            throws OWLOntologyCreationException {
        // given
        OWLOntology o = m.createOntology();
        OWLDataProperty c = DataProperty(IRI("urn:c"));
        OWLDataProperty d = DataProperty(IRI("urn:d"));
        OWLDataProperty e = DataProperty(IRI("urn:e"));
        OWLAxiom ax = SubDataPropertyOf(c, d);
        OWLClass x = Class(IRI("urn:x"));
        OWLAxiom ax2 = DataPropertyDomain(c, x);
        OWLAxiom ax3 = DataPropertyRange(c, Boolean());
        OWLAxiom ax4 = EquivalentDataProperties(c, e);
        o.getOWLOntologyManager().addAxiom(o, ax);
        o.getOWLOntologyManager().addAxiom(o, ax2);
        o.getOWLOntologyManager().addAxiom(o, ax3);
        o.getOWLOntologyManager().addAxiom(o, ax4);
        assertTrue(find().axiomsOfType(AxiomType.SUB_DATA_PROPERTY).in(o)
                .contains(ax));
        assertTrue(find().sub().propertiesOf(d).in(o).contains(c));
        assertTrue(find().sup().propertiesOf(c).in(o).contains(d));
        assertTrue(find().domains(c).in(o).contains(x));
        assertTrue(find().ranges(c).in(o).contains(Boolean()));
        assertTrue(find().equivalent().propertiesOf(c).in(o).contains(e));
    }
}
