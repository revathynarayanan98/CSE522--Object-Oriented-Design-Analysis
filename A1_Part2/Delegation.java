class Delegation {
	
	public static void main(String args[]) {
		B b = new B();
		System.out.println(b.f()+b.g()+b.p(1)+b.q(2));
	
		D d = new D();
		System.out.println(d.f()+d.g()+d.h()+d.p(1)+d.q(2)+d.r());

		B2 b2 = new B2();
		System.out.println(b2.f()+b2.g()+b2.p(1)+b2.q(2));	
		
		D2 d2 = new D2();
		System.out.println(d2.f()+d2.g()+d2.h()+d2.p(1)+d2.q(2)+d2.r());	
		
	}
}

class Delegation2 {
		
	public static void main(String args[]) {
		
		E e = new E();
		System.out.println(e.f()+e.g()+e.h()+e.p(1)+e.q(2)+e.r()+e.k(100));
		
		F f = new F();
		System.out.println(f.f()+f.g()+f.h()+f.p(1)+f.q(2)+f.r()+f.j(10)+f.l(100));

		E2 e2 = new E2();
		System.out.println(e2.f()+e2.g()+e2.h()+e2.p(1)+e2.q(2)+e2.r()+e2.k(100));

		F2 f2 = new F2();
		System.out.println(f2.f()+f2.g()+f2.h()+f2.p(1)+f2.q(2)+f2.r()+f2.j(10)+f2.l(100));		
	}
}


abstract class A {
    public static int a1 = 100;
	protected int a2 = 200;

	public int f() {
		return u(100) + v(200);
	}

	public int u(int m) {return m; }

    public int v(int m) {return m*2; }
	
}

class B extends A {
	public static int b1 = 1000;
	protected int b2 = 2000;

	public int g() {
		return this.p(100) + this.q(200); 
	}

	public int p(int m) {
		return m + a1+b1;
	}

	public int q(int m) {
		return m + a2+b2;
	}
}

abstract class C extends B {
	public static int c1 = 10000;
	protected int c2 = 20000;

	public int r() {
		return f() + g() + h(); 
	}

	public int q(int m) {
		return m + a2 + b2 + c2;
	}

	protected abstract int h();
}

class D extends C {
	public static int d1 = 100000;
	protected int d2 = 200000;

	public int r() {
		return f() + g() + this.h(); 
	}

	public int p(int m) {
		return super.p(m) + d2;
	}

	public int h() {
		return a1 + b1 + c1;
	}
	
	public int j(int n) {
		return r() + super.r();
	}

}

class E extends C {  
	protected int e1 = 1;
	protected int e2 = 2;

	public int q(int m) {
		return p(m) + c2;
	}

	public int h() {
		return a1 + b1 + e1;
	}
	
	public int k(int n) {
		return q(n) + super.q(n);
	}

}

class F extends D {
	protected int f1 = 10; 
	protected int f2 = 20;

	public int q(int m) {
		return p(m) + c1 + d1;
	}

	public int h() {
		return c2 + f2;
	}
	
	public int l(int n) {
		return q(n) + super.q(n);
	}

}



//============= TRANSLATION IN TERMS OF DELEGATION ===============

interface IA {   
         // fill in the details
	int f();
	int u(int m);
	int v(int m);
}

interface IB extends IA {
	// fill in the details
	int g();
	int p(int m);
	int q(int m);
}

interface IC extends IB {
	// fill in the details
	int r();
	int q(int m);
	int h();
}

interface ID extends IC {
	// fill in the details
	int r();
	int p(int m);
	int h();
	int j(int n);
}

interface IE extends IC {
	// fill in the details
	int q(int m);
	int h();
	int k(int n);	
}

interface IF extends ID {
	// fill in the details
	int q(int m);
	int h();
	int l(int n);
}

class A2 implements IA {
	// fill in the details
	int a1 = 100;
	int a2 = 200;
	IA this2;
	public A2(IA e){
		this2 = e;
	}
	
	public int f() {
		return u(100) + v(200);
	}

	public int u(int m) {
		return m;
	}

	public int v(int m) {
		return m*2;
	} 
}

class B2 implements IB {
	// fill in the details
	int b1 = 1000;
	int b2 = 2000;
	A2 a_obj;
	IB this2;
	public B2() {
		a_obj = new A2(this);
		this2 = this;
	}
	public B2(IB e){
		a_obj = new A2(e); 
		this2 = e;
	}
	
	public int f() {
		return a_obj.f();
	}

	public int g() {
		return this2.p(100) + this2.q(200); 
	}

	public int p(int m) {
		return m + a_obj.a1+b1;
	}

	public int q(int m) {
		return m + a_obj.a2+b2;
	}
	/*@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return 0;
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return 0;
	}*/
	@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return a_obj.u(m);
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return a_obj.v(m);
	}
	
	
	
}

class C2 implements IC {
	// fill in the details
	int c1 = 10000;
	int c2 = 20000;
	B2 b_obj;
	IC this2;
	
	public C2(IC e){
		b_obj = new B2(e); 
		this2 = e;
	}
	public int g() {
		return b_obj.g();
	}
	public int p(int m) {
		return b_obj.p(m); 
	}
	public int f() {
		return b_obj.f(); 
	}//return f() + g() + h(); ,return m + a2 + b2 + c2;
	public int r() {
		return f() + g() + h(); 
	}
	public int q(int m) {
		return m + b_obj.a_obj.a2 + b_obj.b2 + c2;
	}
	public int h() {
		return this2.h();
	}
	@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return b_obj.a_obj.u(m);
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return b_obj.a_obj.v(m);
	}

	
	
}

class D2 implements ID {
	// fill in the details
	int d1 = 100000;
	int d2 = 200000;
	C2 c_obj;
	ID this2;
	//return f() + g() + this.h(); , return super.p(m) + d2; , return a1 + b1 + c1; , return r() + super.r();
	public D2() {
		c_obj = new C2(this);
		this2 = this;
	}
	public D2(ID e) {
		c_obj = new C2(e);
		this2 = e;
	}
	public int q(int m) {
		return c_obj.q(m); 
	}
	public int g() {
		return c_obj.g();
	}
	public int f() {
		return c_obj.f(); 
	}
	public int r() {
		return this2.f() + this2.g() + this2.h();
	}
	public int p(int m) {
		return c_obj.p(m) + d2;
	}
	public int h() {
		return c_obj.b_obj.a_obj.a1 + c_obj.b_obj.b1 + c_obj.c1;
	}
	public int j(int n) {
		 return r() + c_obj.r();
	}
	@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return c_obj.b_obj.a_obj.u(m);
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return c_obj.b_obj.a_obj.v(m);
	}
	
}

class E2 implements IE {
        // fill in the details
	int e1 = 1;
	int e2 = 2;
	C2 c_obj;
	//return p(m) + c2; , return a1 + b1 + e1; , return q(n) + super.q(n);
	public E2() {
		c_obj = new C2(this); 	
	}
	public int r() {
		return c_obj.r();
	}
	public int g() {
		return c_obj.g();
	}
	public int p(int m) {
		return c_obj.p(m);
	}
	public int f() {
		return c_obj.f();
	}
	public int q(int m) {
		return p(m) + c_obj.c2; 
	}
	public int h() {
		return c_obj.b_obj.a_obj.a1 + c_obj.b_obj.b1 + e1;
	}
	public int k(int n) {
		return q(n) + c_obj.q(n);
	}
	@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return c_obj.b_obj.a_obj.u(m);
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return c_obj.b_obj.a_obj.v(m);
	}
	 
}

class F2 implements IF{   //return p(m) + c1 + d1; , return c2 + f2; , return q(n) + super.q(n);
	// fill in the details
	int f1 = 10;
	int f2 = 20;
	D2 d_obj;
	public F2() {
		d_obj = new D2(this);
	}
	public int r() {
		return d_obj.r();
	}
	public int p(int m) {
		return d_obj.p(m);
	}
	public int j(int n) {
		return d_obj.j(n);
	}
	public int g() {
		return d_obj.g();
	}
	public int f() {
		return d_obj.f();
	}
	public int q(int m) {
		return p(m) + d_obj.c_obj.c1 + d_obj.d1;
	}
	public int h() {
		return d_obj.c_obj.c2 + f2;
	}
	public int l(int n) {
		return q(n) + d_obj.q(n);
	}
	@Override
	public int u(int m) {
		// TODO Auto-generated method stub
		return d_obj.c_obj.b_obj.a_obj.u(m);
	}
	@Override
	public int v(int m) {
		// TODO Auto-generated method stub
		return d_obj.c_obj.b_obj.a_obj.v(m);
	}
	
}