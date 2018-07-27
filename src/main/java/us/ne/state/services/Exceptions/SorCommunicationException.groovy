package us.ne.state.services.Exceptions

class SorCommunicationException extends RuntimeException{

    SorCommunicationException(String var1) {
        super(var1)
    }

    SorCommunicationException(String var1, Throwable var2) {
        super(var1, var2)
    }
}
