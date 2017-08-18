package org.eclipse.winery.TestConsistency.entitytypes;

import org.eclipse.winery.common.ids.definitions.ArtifactTypeId;
import org.eclipse.winery.common.ids.definitions.RelationshipTypeId;
import org.eclipse.winery.common.ids.definitions.TOSCAComponentId;
import org.eclipse.winery.repository.backend.Repository;
import org.eclipse.winery.repository.export.TOSCAExportUtil;
import org.eclipse.winery.repository.resources.AbstractResourceTest;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.fail;

/**
 * Created by Lati on 11.08.17.
 */
public class TestConsistencyRelationshipTypeId extends AbstractResourceTest {

	/**
	 * testConsistency - checks consistency between Repository and its linked elements for Duplicates.
	 * @throws Exception
	 */
	@Test
	public void testConsistency() throws Exception{
		this.setRevisionTo("c25aa724201824fce6eddcc7c35a666c6e015880"); // prepare local Repository.
		List<String> errors = new ArrayList<>(); // collect Errors in list.
		Set<RelationshipTypeId> list = Repository.INSTANCE.getAllTOSCAComponentIds(RelationshipTypeId.class);
		for (RelationshipTypeId id : list) { // Iterate all ArtifactTemplateId
			Collection<TOSCAComponentId> linked = TOSCAExportUtil.getReferencedTOSCAComponentIds(id);
			// get All Linke TOSCAComponentIds
			// Hint 3: now we gotall Linked objects, next  check if they are part oft the Repository, too.
			// If they  are part of the Repository you should add the name of the main 
			// Object you are Iterating and the found linked object. If there are collected Errors the Test should Fail!
		}
	}
}
