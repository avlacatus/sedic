package ro.infoiasi.sedic.android.util;

import ro.infoiasi.sedic.android.communication.task.Response;

public interface EntityOperationsCallback<E> {

	public static final String CALLBACK_ADD_ENTITY_STARTED = "callback_add_entity_started";
	public static final String CALLBACK_ADD_ENTITY_FINISHED = "callback_add_entity_finished";
	public static final String CALLBACK_DELETE_ENTITY_STARTED = "callback_delete_entity_started";
	public static final String CALLBACK_DELETE_ENTITY_FINISHED = "callback_delete_entity_finished";
	public static final String CALLBACK_GET_ENTITIES_STARTED = "callback_get_entities_started";
	public static final String CALLBACK_GET_ENTITIES_FINISHED = "callback_get_entities_finished";

	public void onEntityOperationStarted();

	public void onAddEntityOperationFinished();
	public void onGetEntitiesOperationFinished(Response<E> result);
	public void onUpdateEntityOperationFinished();
	public void onDeleteEntityOperationFinished();


}
