/*******************************************************************************
 * Copyright (c) 2008 Industrial TSI and Maarten Meijer.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Maarten Meijer - initial API and implementation
 *******************************************************************************/

package com.industrialtsi.mylyn.core.persistence;

import java.sql.SQLException;
import java.util.List;

import org.eclipse.core.runtime.CoreException;

import com.industrialtsi.mylyn.core.dto.IndustrialComment;
import com.industrialtsi.mylyn.core.dto.IndustrialQueryParams;
import com.industrialtsi.mylyn.core.dto.IndustrialTask;
import com.industrialtsi.mylyn.core.persistence.IPersistor;
import com.industrialtsi.mylyn.core.persistence.IbatisPersistor;
import com.industrialtsi.mylyn.test.db.core.DerbyTestCase;

/**
 * <code>IbatisPersistorTest</code> : TODO describe.
 * 
 * @author Maarten
 * 
 */
public class IbatisPersistorTest extends DerbyTestCase {

	/* @see junit.framework.TestCase#setUp() */
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		initTestRepository();
		assertNotNull("repository not inited", repository);
		setDefaultCredentials(repository);
	}

	/* @see junit.framework.TestCase#tearDown() */
	@Override
	protected void tearDown() throws Exception {
		super.tearDown();
	}

	private IPersistor createPersistor() {
		// TODO Auto-generated method stub
		return new IbatisPersistor();
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#fetchAdditional(org.eclipse.mylyn.tasks.core.TaskRepository, java.lang.String[])}
	 * .
	 */
	public void testFetchAdditional() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#fetchAttachments(org.eclipse.mylyn.tasks.core.TaskRepository, java.lang.String[])}
	 * .
	 */
	public void testFetchAttachments() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#fetchComments(org.eclipse.mylyn.tasks.core.TaskRepository, java.lang.String[])}
	 * .
	 */
	public void testFetchComments() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#fetchTask(org.eclipse.mylyn.tasks.core.TaskRepository, java.lang.String[])}
	 * .
	 */
	public void testFetchTask() {
		IndustrialQueryParams criteria = new IndustrialQueryParams("priority=P3&status=NEW");

		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.findTasks(repository, criteria);

			assertNotNull("findTasks returns null", found);
			assertTrue("findTasks returns empty", !found.isEmpty());

			for (String key : found) {
				IndustrialTask task = persistor.fetchTask(repository, key);

				assertNotNull("fetchTask returns null", task);
				assertEquals("Fetched task Priority wrong", "P3", task.getPriority());
				assertEquals("Fetched task Status wrong", "NEW", task.getIssueStatus());
			}

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#persistTask(org.eclipse.mylyn.tasks.core.TaskRepository, com.industrialtsi.mylyn.core.dto.IndustrialTask)}
	 * .
	 */
	public void testPersistTask() {

		IPersistor persistor = createPersistor();
		IndustrialQueryParams all = new IndustrialQueryParams("");
		
		try {
			
			List<String> found = persistor.findTasks(repository, all);
			int before = found.size();
			
			IndustrialTask task = new IndustrialTask(repository.getUrl(), null, "Unit Test Generated");
			
			task.setPriority("P4");
			task.setIssueStatus("NEW");
			task.setOwner("junit");
			task.setNotes("Notes generated by code");
			task.setReporter("junit");
			task.setProduct("UNKNOWN");
			
			String id = persistor.persistTask(repository, task);

			assertNotNull("persistTask returned null", id);
			
			found = persistor.findTasks(repository, all);
			int after = found.size();
			
			assertEquals("Size not increased by one", before + 1, after);

		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#persistComment(org.eclipse.mylyn.tasks.core.TaskRepository, com.industrialtsi.mylyn.core.dto.IndustrialComment)}
	 * .
	 */
	public void testPersistComment() {
		IPersistor persistor = createPersistor();
		IndustrialQueryParams all = new IndustrialQueryParams("");

		try {

			List<String> found = persistor.findTasks(repository, all);
			String last = found.get(found.size() - 1);

			List<IndustrialComment> comments = persistor.fetchComments(repository, last);
			int before = comments.size();
			
			IndustrialComment comment = new IndustrialComment();
			
			comment.setAuthor("test");
			comment.setAuthorName("testPersistComment");
			comment.setDescription("Somem new comment");
			comment.setTaskId(last);
			comment.setText("some text");
			
			persistor.persistComment(repository, comment);

			comments = persistor.fetchComments(repository, last);
			int after = comments.size();

			assertEquals("Size not increased by one", before + 1, after);

		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#persistAttachment(org.eclipse.mylyn.tasks.core.TaskRepository, com.industrialtsi.mylyn.core.dto.IndustrialAttachment)}
	 * .
	 */
	public void testPersistAttachment() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#updateTask(org.eclipse.mylyn.tasks.core.TaskRepository, com.industrialtsi.mylyn.core.dto.IndustrialTask)}
	 * .
	 */
	public void testUpdateTask() {
		IndustrialQueryParams criteria = new IndustrialQueryParams("priority=P3&status=NEW");

		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.findTasks(repository, criteria);

			assertNotNull("findTasks returns null", found);
			assertTrue("findTasks returns empty", !found.isEmpty());

			for (String key : found) {
				IndustrialTask task = persistor.fetchTask(repository, key);
				assertNotNull("fetchTask returns null", task);
				assertEquals("Fetched task Priority wrong", "P3", task.getPriority());
				task.setPriority("P5");
				persistor.updateTask(repository, task);
			}

			List<String> empty = persistor.findTasks(repository, criteria);

			assertNotNull("findTasks returns null", empty);
			assertTrue("findTasks returns non-empty", empty.isEmpty());

			for (String key : found) {
				IndustrialTask task = persistor.fetchTask(repository, key);
				assertNotNull("fetchTask returns null", task);
				assertEquals("Fetched task Priority wrong", "P5", task.getPriority());
				task.setPriority("P3");
				persistor.updateTask(repository, task);
			}

			empty = persistor.findTasks(repository, criteria);

			assertNotNull("findTasks returns null", empty);
			assertTrue("findTasks returns empty", !empty.isEmpty());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#fetchAttachmentBlob(org.eclipse.mylyn.tasks.core.TaskRepository, java.lang.String)}
	 * .
	 */
	public void testFetchAttachmentBlob() {
		fail("Not yet implemented");
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#findTasks(org.eclipse.mylyn.tasks.core.TaskRepository, com.industrialtsi.mylyn.core.dto.IndustrialQueryParams)}
	 * .
	 */
	public void testFindTasks() {
		IndustrialQueryParams criteria = new IndustrialQueryParams("priority=P3&status=NEW");

		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.findTasks(repository, criteria);

			assertNotNull("findTasks returns null", found);
			assertTrue("findTasks returns empty", !found.isEmpty());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#getLegalOwners(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testGetLegalOwners() {
		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.getLegalOwners(repository);

			assertNotNull("getLegalOwners returns null", found);
			assertTrue("getLegalOwners returns empty", !found.isEmpty());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#getLegalPriorities(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testGetLegalPriorities() {
		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.getLegalPriorities(repository);

			assertNotNull("getLegalPriorities returns null", found);
			assertTrue("getLegalPriorities returns empty", !found.isEmpty());

			assertEquals("getLegalPriorities is wrong size", 5, found.size());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#getLegalProducts(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testGetLegalProducts() {
		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.getLegalProducts(repository);

			assertNotNull("getLegalProducts returns null", found);
			assertTrue("getLegalProducts returns empty", !found.isEmpty());

			assertEquals("getLegalProducts is wrong size", 6, found.size());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#getLegalIssueStatus(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testGetLegalIssueStatus() {
		IPersistor persistor = createPersistor();

		try {
			List<String> found = persistor.getLegalIssueStatus(repository);

			assertNotNull("getLegalIssueStatus returns null", found);
			assertTrue("getLegalIssueStatus returns empty", !found.isEmpty());

			assertEquals("getLegalIssueStatus is wrong size", 3, found.size());

			// TODO add more tests
		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#validate(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testValidate() {
		IPersistor persistor = createPersistor();

		try {
			boolean validated = persistor.validate(repository);

			assertTrue("Not validated", validated);

		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#canInitialize(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testCanInitialize() {
		IPersistor persistor = createPersistor();

		try {
			boolean canInit = persistor.canInitialize(repository);

			assertTrue("Cannot initialize", !canInit);

		} catch (SQLException e) {
			fail(e.getMessage());
		} catch (CoreException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test method for
	 * {@link com.industrialtsi.mylyn.core.persistence.IbatisPersistor#initialize(org.eclipse.mylyn.tasks.core.TaskRepository)}
	 * .
	 */
	public void testInitialize() {
		fail("Not yet implemented");
	}

}
