package ok.logvidmi.accounting.common.repo

interface IContactRepository {

    suspend fun create(request: ContactDbRequest): ContactDbResponse

    suspend fun read(request: ContactIdRequest): ContactDbResponse

    suspend fun update(request: ContactDbRequest): ContactDbResponse

    suspend fun delete(request: ContactIdRequest): ContactDbResponse

    suspend fun filter(filter: ContactFilterRequest): ContactsDbResponse

    companion object NONE: IContactRepository {
        override suspend fun create(request: ContactDbRequest): ContactDbResponse {
            TODO("Not yet implemented")
        }

        override suspend fun read(request: ContactIdRequest): ContactDbResponse {
            TODO("Not yet implemented")
        }

        override suspend fun update(request: ContactDbRequest): ContactDbResponse {
            TODO("Not yet implemented")
        }

        override suspend fun delete(request: ContactIdRequest): ContactDbResponse {
            TODO("Not yet implemented")
        }

        override suspend fun filter(filter: ContactFilterRequest): ContactsDbResponse {
            TODO("Not yet implemented")
        }

    }
}