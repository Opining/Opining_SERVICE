package br.com.opining.util.validation;

import br.com.opining.library.model.error.OpiningError;

public interface DataValidator<T, ID> {
	
	public OpiningError validateInsertion(T newEntity);
	public OpiningError validateUpdate(ID oldID, T newEntity);
		
}
