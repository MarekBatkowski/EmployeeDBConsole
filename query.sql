CREATE TABLE public.employee
(
    id serial NOT NULL,
    name character varying(30) NOT NULL,
    surname character varying(50) NOT NULL,
    email character varying(50) NOT NULL,
    PRIMARY KEY (id)
);

ALTER TABLE public.employee
    OWNER to postgres;

INSERT INTO public.employee(name, surname, email) VALUES ('Jan', 'Kowalski', 'jkowalsk@exampledomain.com');
INSERT INTO public.employee(name, surname, email) VALUES ('Maciej', 'Nowak', 'mnowak@exampledomain.com');
INSERT INTO public.employee(name, surname, email) VALUES ('Marzena', 'Wisniewska', 'mwisniewska@exampledomain.com');