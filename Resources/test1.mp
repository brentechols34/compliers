program Program3;
  var A : integer;
      B : float;
      C : integer;
   begin
     A:=0.0;
     for A:= 1 to 10 do
         Writeln(A);
     Write('Enter an integer for A: ');
     Read(A);
     Writeln ('A = ', A);
     Write('Enter a float for B: ');
     Read(B);
     Writeln('B = ', B);
     Write('Enter an integer for C: ');
     Read(C);
     Writeln('C = ', C);
     A := 3*A*A*A + 5*B*B - C;
     Write('A = ', A, ', B = ', B,', and C = ', C);
     Repeat
     	A := A + 1
     Until 1 = 1
  end.