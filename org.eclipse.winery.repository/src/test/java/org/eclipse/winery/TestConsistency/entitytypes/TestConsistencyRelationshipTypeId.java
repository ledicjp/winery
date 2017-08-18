package org.eclipse.winery.TestConsistency.entitytypes;

import org.eclipse.winery.common.ids.definitions.ArtifactTypeId;
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
		// Hint 1: Get List of all ComponentIds from Type RelationshipTypeId and 
		// declare a List for all found Errors - 
		// Get a valid Repository Commit Id and get your repository ready for the Test
	}
}
