class MyClass {
Foo f;
MyClass() {
Foo f = new Foo();
meth(new Bar());
}
void meth(Bar bell) {
f.add(bell);
}
}
