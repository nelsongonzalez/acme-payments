package com.acme.payments;

public final class Help {

    private static final String WHITE_BOLD = "\033[1;37m";

    public static final String RED_BOLD = "\033[1;31m";

    private static final String RESET = "\033[0m";

    public void usage() {
        StringBuilder help = new StringBuilder();
        help.append("\n");
        help.append(WHITE_BOLD);
        help.append("Usage:\n");
        help.append(RESET);
        help.append("\tjava -jar acme-payments.jar <schedule_file>\n\n");
        help.append(WHITE_BOLD);
        help.append("Where:\n");
        help.append(RESET);
        help.append(WHITE_BOLD);
        help.append("\t<schedule_file> ");
        help.append(RESET);
        help.append("is the path to the .txt file containing at least five lines,\n");
        help.append("\tevery one representing the schedule of an employee.\n\n");
        help.append(WHITE_BOLD);
        help.append("File content:\n");
        help.append(RESET);
        help.append("\tEvery line in the file must follow the format:\n\n");
        help.append("\t<employee_name>=<day><time_from>-<time_to>[,<day><time_from>-<time_to>]\n\n");
        help.append("Where:\n");
        help.append(WHITE_BOLD);
        help.append("\t<employee_name> ");
        help.append(RESET);
        help.append("is the name of the employee.\n");
        help.append(WHITE_BOLD);
        help.append("\t<day> ");
        help.append(RESET);
        help.append("is the first two letters of the day\n");
        help.append(WHITE_BOLD);
        help.append("\t<time_from> ");
        help.append(RESET);
        help.append("is the start time in hh:mm format.\n");
        help.append(WHITE_BOLD);
        help.append("\t<time_to> ");
        help.append(RESET);
        help.append("is the end time in hh:mm format.\n\n");
        help.append(WHITE_BOLD);
        help.append("\tFor example:\n");
        help.append(RESET);
        help.append("\tRENE=MO10:00-12:00,TU10:00-12:00,TH01:00-03:00,SA14:00-18:00,SU20:00-21:00");
        print(help.toString());
    }

    public void invalidArguments() {
        StringBuilder error = new StringBuilder();
        error.append("\n");
        error.append(RED_BOLD);
        error.append("ERROR: The file does not exists.\n");
        error.append(RESET);
        print(error.toString());
    }

    private void print(String value) {
        System.out.println(value);
    }
}
