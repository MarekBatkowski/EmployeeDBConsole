import java.util.regex.Pattern;

public class RegExContainer
{
    static final Pattern emailRegEx = Pattern.compile("[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,4}");
    static final Pattern nameRegEx = Pattern.compile("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{2,29}");
    static final Pattern surNameRegEx = Pattern.compile("[A-ZĄĆĘŁŃÓŚŹŻ][a-ząćęłńóśźż]{2,49}");
    static final Pattern number = Pattern.compile("-?[0-9]+$");
}
