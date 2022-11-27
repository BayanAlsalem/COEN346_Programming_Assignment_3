public class Page {

    String variable_id;
    long variable_value; //unsigned integer value
    int last_access; // determines when was the page last accessed.

    public Page(String variable_id, long variable_value) {
        this.variable_id = variable_id;
        this.variable_value = variable_value;
        last_access = 0; // at first the last_access to a page is 0, no process has access it.
    }

    public Page()

    {
        variable_id="";
        variable_value=0;
        last_access=0;

    }
    public int getLast_access()
    {
        return last_access;
    }



}
