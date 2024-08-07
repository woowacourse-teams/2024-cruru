import { KeyedStrings } from '@customTypes/utilTypes';
import { useState } from 'react';

interface UseFormProps<T> {
  initialValues: T;
}

export default function useForm<T>({ initialValues }: UseFormProps<T>) {
  const [formValues, setFormValues] = useState<T>(initialValues);
  const [errors, setErrors] = useState<KeyedStrings<T>>({} as KeyedStrings<T>);

  return { formValues };
}
